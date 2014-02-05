(ns guestbook.routes.home
  (:use compojure.core)
  (:require [guestbook.views.layout :as layout]
            [guestbook.util :as util]
            [guestbook.models.db :as db]))

(defn home-page [& [name message error]]
  (layout/render "home.html"
  	{:error error
  	 :message message
  	 :name name
  	 :messages (db/get-messages)}))

(defn save-message [name message]
	(cond
		(empty? name)
		(home-page name message "Someone forgot to leave a name")

		(empty? message)
		(home-page name message "Message was empty")

		:else
		(do
			(db/save-message name message)
			(home-page))))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (POST "/" [name message] (save-message name message))
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

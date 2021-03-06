(ns guestbook.routes.home
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]
            [guestbook.models.db :as db]
  ))

(defn show-guests []
  [:ul.guests
    (for [{:keys [message name timestamp]} (db/read-guest-comments)]
      [:li
        [:blockquote message]
        [:p "- " [:cite name]]
        [:time timestamp]
      ])])

(defn home [& [name message error]]
  (layout/common
     [:h1 "Guestbook"]
     [:p "Welcome to the guestbook"]
     [:p error]

     (show-guests)

     [:hr]
     (form-to [:post "/"]
              [:p "Name: "]
              (text-field "name" name)
              [:p "Message: "]
              (text-area {:rows 10 :cols 40} "message" message)
              [:br]
              (submit-button "comment"))

   ))

(defn save-guest-comment [name message]
  (cond
     (empty? name)
     (home name message "Oops, no name given!")
     (empty? message)
     (home name message "You need to give a message")
     :else
     (do
       (db/save-guest-comment name message)
       (home)
     )))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [name message] (save-guest-comment name message))
)

(ns guestbook.routes.home
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]))

(defn home []
  (layout/common
     [:h1 "Guestbook"]
     [:p "Welcome to the guestbook"]
     [:hr]
     [:form
        [:p "Name: "]
        [:input]
        [:p "Message: "]
        [:textarea {:rows 10 :cols 40}]
     ]
   ))

(defroutes home-routes
  (GET "/" [] (home)))

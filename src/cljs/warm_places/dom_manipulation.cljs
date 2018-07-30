(ns warm_places.dom_manipulation
  (:require [warm_places.state :refer [handle-click-in-cities
                                      handle-click-in-wishlist
                                      get-cities
                                      get-wishlist]]
            [warm_places.html_elements :refer [cities-list
                                               wishlist-list]]))

(enable-console-print!)

(defn update-dom []
  (update-cities-in-dom (get-cities))
  (update-wishlist-in-dom (get-wishlist)))

(defn click-in-cities-wrapper [city]
  (handle-click-in-cities city)
  (update-dom))

(defn click-in-wishlist-wrapper [city]
  (handle-click-in-wishlist city)
  (update-dom))

(defn clean-list [list-name] 
  (-> (.getElementById js/document list-name)
    (.-innerHTML)
    (set! "")))

(defn add-city-to-dom-wishlist [city]
  (let [li-node (.createElement js/document "li")
        text-node (.createTextNode js/document(str (:name city) ": " (:temp city)))]
        (.appendChild li-node text-node)
        (.addEventListener li-node "click" #(click-in-wishlist-wrapper city))
        (.appendChild (.getElementById js/document wishlist-list) li-node)))

(defn add-city-to-dom-cities [city]
  (let [li-node (.createElement js/document "li")
        text-node (.createTextNode js/document(str (:name city) ": " (:temp city)))]
        (.appendChild li-node text-node)
        (.addEventListener li-node "click" #(click-in-cities-wrapper city))
        (.appendChild (.getElementById js/document cities-list) li-node)))

;(defn add-city-to-cities-ul [city]
 ; (add-element-to-dom city cities-list click-in-cities-wrapper))

;(defn add-city-to-wishlist-ul [city] 
 ; (add-element-to-dom city wishlist-list click-in-wishlist-wrapper))

(defn update-cities-in-dom [list-of-cities]
  (clean-list cities-list)
  (mapv add-city-to-dom-cities list-of-cities))

(defn update-wishlist-in-dom [list-of-cities-in-wishlist]
  (clean-list wishlist-list)
  (mapv add-city-to-dom-wishlist list-of-cities-in-wishlist))

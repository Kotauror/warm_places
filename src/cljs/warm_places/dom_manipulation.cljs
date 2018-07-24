(ns warm_places.dom_manipulation
  (:require [warm_places.state :refer [handle-click-in-cities
                                      handle-click-in-wishlist
                                      get-cities
                                      get-wishlist]]))

(enable-console-print!)

(defn click-in-cities-wrapper [city]
  (handle-click-in-cities city)
  (update-cities-in-dom (get-cities))
  (update-wishlist-in-dom (get-wishlist))
)

(defn click-in-wishlist-wrapper [city]
  (handle-click-in-wishlist city)
  (update-cities-in-dom (get-cities))
  (update-wishlist-in-dom (get-wishlist))
)

(defn clean-list [list-name] 
  (-> (.getElementById js/document list-name)
    (.-innerHTML)
    (set! "")))

(defn add-element-to-dom [city container-name function]
  (let [li-node (.createElement js/document "li")
        text-node (.createTextNode js/document city)]
        (.appendChild li-node text-node)
        (.addEventListener li-node "click" #(function city))
        (.appendChild (.getElementById js/document container-name) li-node)))

(defn add-city-to-dom-list [city]
  (add-element-to-dom city "cities" click-in-cities-wrapper))

(defn add-city-to-wishlist-dom-list [city] 
  (add-element-to-dom city "wishlist" click-in-wishlist-wrapper))

(defn update-cities-in-dom [list-of-cities]
  (clean-list "cities")
  (mapv add-city-to-dom-list list-of-cities))

(defn update-wishlist-in-dom [list-of-cities-in-wishlist] 
  (clean-list "wishlist")
  (mapv add-city-to-wishlist-dom-list list-of-cities-in-wishlist))


;; later:
;; pass to add-city-to-dom-list method of line 9 and contener from line 10.

;;rerender :D deletes stuff and rerender from atoms.

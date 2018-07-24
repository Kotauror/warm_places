(ns warm_places.dom_manipulation
  (:require [warm_places.state :refer [handle-click-in-cities
                                      get-cities
                                      get-wishlist]]))

(enable-console-print!)

(defn on-click-wrapper [city]
  (handle-click-in-cities city)
  (update-cities-in-dom (get-cities))
  (update-wishlist-in-dom (get-wishlist))
)

(defn clean-list [list-name] 
  (-> (.getElementById js/document list-name)
    (.-innerHTML)
    (set! "")))

(defn add-city-to-dom-list [city]
  (let [li-node (.createElement js/document "li")
        text-node (.createTextNode js/document city)]
        (.appendChild li-node text-node)
        (.addEventListener li-node "click" #(on-click-wrapper city))
        (.appendChild (.getElementById js/document "cities") li-node)))

(defn update-cities-in-dom [list-of-cities]
  (clean-list "cities")
  (mapv add-city-to-dom-list list-of-cities))

(defn add-city-to-wishlist-dom-list [city] 
  (let [li-node (.createElement js/document "li")
      text-node (.createTextNode js/document city)]
      (.appendChild li-node text-node)
     ; (.addEventListener li-node "click" #(function))
      (.appendChild (.getElementById js/document "wishlist") li-node)))

(defn update-wishlist-in-dom [list-of-cities-in-wishlist] 
  (clean-list "wishlist")
  (mapv add-city-to-wishlist-dom-list list-of-cities-in-wishlist))


;; later:
;; pass to add-city-to-dom-list method of line 9 and contener from line 10.

;;rerender :D deletes stuff and rerender from atoms.

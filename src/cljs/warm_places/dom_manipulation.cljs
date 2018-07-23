(ns warm_places.dom_manipulation
  (:require [warm_places.state :refer [handle-click-in-cities
                                      get-cities]]))

(enable-console-print!)

(defn on-click-wrapper [city]
  (handle-click-in-cities city)
  (update-cities-in-dom (get-cities)))

(defn add-city-to-dom-list [city]
  (let [li-node (.createElement js/document "li")
        text-node (.createTextNode js/document city)]
        (.appendChild li-node text-node)
        (.addEventListener li-node "click" #(on-click-wrapper city))
        (.appendChild (.getElementById js/document "cities") li-node)))

(defn update-cities-in-dom [list-of-cities]
  (mapv add-city-to-dom-list list-of-cities))

;; later:
;; pass to add-city-to-dom-list method of line 9 and contener from line 10.

;;rerender :D deletes stuff and rerender from atoms.

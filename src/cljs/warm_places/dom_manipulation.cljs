(ns warm_places.dom_manipulation)

(enable-console-print!)

(defn add-city-to-dom-list [city]
  (let [li-node (.createElement js/document "li")
        text-node (.createTextNode js/document city)]
        (.appendChild li-node text-node)
        ; (.addEventListener li-node "click") ;;delete from main add to wish)
        (.appendChild (.getElementById js/document "cities") li-node)))

(defn update-cities-in-dom [list-of-cities]
  (mapv add-city-to-dom-list list-of-cities))

;; later:
;; pass to add-city-to-dom-list method of line 9 and contener from line 10.

;;rerender :D deletes stuff and rerender from atoms.

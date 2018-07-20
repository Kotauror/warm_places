(ns warm_places.dom_manipulation)

(enable-console-print!)

(defn add-city-to-dom-list [city]
  (let [li-node (.createElement js/document "LI")]
        [text-node (.createTextNode js/document city)]
        (.appendChild li-node text-node)
        (.appendChild (.getElementById js/document "cities") li-node)))

(defn update-dom [list-of-cities]
  (mapv add-city-to-dom-list list-of-cities))

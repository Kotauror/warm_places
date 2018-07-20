(ns warm_places.state)

(enable-console-print!)

(def longitude (atom ""))
(def latitude (atom ""))
(def radius (atom ""))
(def cities (atom []))

(defn update-latitude [value]
  (swap! latitude (fn [] value)))

(defn update-longitude [value]
  (swap! longitude (fn [] value)))

(defn update-radius [value]
  (swap! radius (fn [] value)))

(defn update-cities-state [new-cities]
  (swap! cities (fn [] new-cities))
  ; (console.log @cities)
  )

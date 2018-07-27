(ns warm_places.state)

(enable-console-print!)

(def longitude (atom ""))
(def latitude (atom ""))
(def radius (atom ""))
(def cities (atom []))
(def wishlist (atom []))

(defn update-latitude [value]
  (swap! latitude (fn [] value)))

(defn update-longitude [value]
  (swap! longitude (fn [] value)))

(defn update-radius [value]
  (swap! radius (fn [] value)))

(defn update-cities-state [new-cities]
  (swap! cities (fn [] new-cities)))

(defn add-to-wishlist [city]
  (swap! wishlist conj city))

(defn add-to-cities [city]
  (swap! cities conj city))

(defn remove-element-from-atom [element atom]
  (swap! atom (partial remove #{element})))

(defn handle-click-in-cities [city] 
  (add-to-wishlist city)
  (remove-element-from-atom city cities))

(defn handle-click-in-wishlist [city]
  (remove-element-from-atom city wishlist))

(defn get-cities []
   @cities)

(defn get-wishlist [] 
  @wishlist)

(defn reset-vector-atom [vector-atom] 
  (reset! vector-atom []))

(ns warm_places.core
  (:require
 [clojure.string :refer [capitalize]]
 [domina :as dom]
 [domina.css :as css]
 [domina.events :as events]))

(enable-console-print!)

(def latitude (atom ""))

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?lat=" latitude "&lng=" longitude "&cities=cities1000&radius=" radius "&username=kotaur"))

(defn get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

(defn add-listener[]
  (events/listen! (js/document.getElementById "submit") :click (fn [event]
    (let [latitude (.-value (.getElementById js/document "latitude"))
          longitude (.-value (.getElementById js/document "longitude"))
          radius (.-value (.getElementById js/document "radius"))]))))

(defn update-latitude [value]
 (swap! latitude (fn [] value)))

(defn value-for-event [event] (.-value (.-target event)))

(defn update-latitude-from-event [event] (update-latitude (value-for-event event)))

(defn add-latitude-input-listener []
 (.addEventListener (.getElementById js/document "latitude") "change" update-latitude-from-event))

(defn log-json [response]
  (js/console.log response)
  (.then (.json response) get-city-names))

(set! (.-onload js/window) (fn []
                            (add-listener)
                            (add-latitude-input-listener)))
; (.then (js/fetch "http://api.geonames.org/findNearbyPlaceNameJSON?lat=50.058144&lng=19.959547&cities=cities1000&radius=100&username=kotaur") log-json)

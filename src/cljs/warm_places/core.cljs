(ns warm_places.core
  (:require
 [clojure.string :refer [capitalize]]
 [domina :as dom]
 [domina.css :as css]
 [domina.events :as events]))

(enable-console-print!)

(defn print-to-console [latitude longitude radius]
  (js/console.log "Form was submitted")
  (js/console.log latitude)
  (js/console.log longitude)
  (js/console.log radius))

(defn api-url [latitude longitude radius]
 (str "http://getnearbycities.geobytes.com/GetNearbyCities?radius=" radius "&latitude=" latitude "&longitude=" longitude))

(defn get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

(defn add-listener[]
  (events/listen! (js/document.getElementById "submit") :click (fn [event]
    (let [latitude (.-value (.getElementById js/document "latitude"))
          longitude (.-value (.getElementById js/document "longitude"))
          radius (.-value (.getElementById js/document "radius"))]
    (print-to-console latitude longitude radius)
    (events/prevent-default event)))))

(defn log-json [response]
  (js/console.log response)
  (.then (.json response) get-city-names))

(set! (.-onload js/window) add-listener)
; (.then (js/fetch "http://api.geonames.org/findNearbyPlaceNameJSON?lat=50.058144&lng=19.959547&cities=cities1000&radius=100&username=kotaur") log-json)

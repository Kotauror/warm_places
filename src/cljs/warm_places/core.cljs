(ns warm_places.core
  (:require
 [clojure.string :refer [capitalize]]
 [domina :as dom]
 [domina.css :as css]
 [domina.events :as events]))

(enable-console-print!)

(def longitude (atom ""))
(def latitude (atom ""))
(def radius (atom ""))

(defn update-latitude [value]
  (swap! latitude (fn [] value)))

(defn update-longitude [value]
  (swap! longitude (fn [] value)))

(defn update-radius [value]
  (swap! radius (fn [] value)))

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

(defn value-for-event [event] (.-value (.-target event)))

(defn wrap-function-for-event [handler-function]
  (fn [event] (handler-function (value-for-event event))))

(defn add-latitude-input-listener []
 (.addEventListener
  (.getElementById js/document "latitude")
  "change"
  (wrap-function-for-event update-latitude)))

(defn add-longitude-input-listener []
 (.addEventListener
  (.getElementById js/document "longitude")
  "change"
  (wrap-function-for-event update-longitude)))

(defn add-radius-input-listener []
  (.addEventListener
    (.getElementById js/document "radius")
    "change"
    (wrap-function-for-event update-radius)))

(defn log-json [response]
  (js/console.log response)
  (.then (.json response) get-city-names))

(set! (.-onload js/window) (fn []
                            (add-listener)
                            (add-latitude-input-listener)
                            (add-longitude-input-listener)
                            (add-radius-input-listener)
))
; (.then (js/fetch "http://api.geonames.org/findNearbyPlaceNameJSON?lat=50.058144&lng=19.959547&cities=cities1000&radius=100&username=kotaur") log-json)

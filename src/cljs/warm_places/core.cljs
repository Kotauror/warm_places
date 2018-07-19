(ns warm_places.core
  (:require [speclj.core]
            [warm_places.state :refer [update-longitude
                                      update-latitude
                                      update-radius]]))

(enable-console-print!)

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?lat=" latitude "&lng=" longitude "&cities=cities1000&radius=" radius "&username=kotaur"))

(defn get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

(defn value-for-event [event] (.-value (.-target event)))

(defn wrap-function-for-event [handler-function]
  (fn [event] (handler-function (value-for-event event))))

(defn add-form-submit-listener []
  (.addEventListener
    (.getElementById js/document "submit")
    "click"
    (fn [] (console.log "hehheh"))))

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
                            (add-form-submit-listener)
                            (add-latitude-input-listener)
                            (add-longitude-input-listener)
                            (add-radius-input-listener)
))
; (.then (js/fetch "http://api.geonames.org/findNearbyPlaceNameJSON?lat=50.058144&lng=19.959547&cities=cities1000&radius=100&username=kotaur") log-json)

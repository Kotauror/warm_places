(ns warm_places.api_call
  (:require [warm_places.state :refer [update-cities-state
                                      get-cities]]
            [warm_places.dom_manipulation :refer [update-cities-in-dom]]))

(enable-console-print!)

(defn geonames-api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=" latitude "&lng=" longitude "&cities=cities5000&radius=" radius "&username=kotaur"))

(defn weather-api-url [city] 
  (str "http://api.openweathermap.org/data/2.5/weather?q=" city "&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"))

(defn get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (->> json
    (.-geonames)
    (mapv get-city-name)))

(defn get-temperature [json]
  (str (.-temp (.-main json))))

(defn update-cities-from-json [json]
  (-> json
    (get-city-names)
    (update-cities-state))
  (update-cities-in-dom (get-cities)))

;untested
(defn fetch [url]
  (js/fetch url))

;untested
(defn use-json [response callback]
  (.then (.json response) callback))

;untested
(defn extract-data [response-promise callback]
  (.then response-promise #(use-json %1 callback)))

(defn call-geonames-api [latitude longitude radius callback]
  (-> (geonames-api-url latitude longitude radius)
    (fetch)
    (extract-data callback)))

(defn handle-get-cities-click [latitude longitude radius]
 (call-geonames-api latitude longitude radius update-cities-from-json))

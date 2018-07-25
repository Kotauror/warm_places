(ns warm_places.weather_api)
 ; (:require [warm_places.api_call :refer [fetch
  ;                                        use-json]]))

(enable-console-print!)

(defn fetch [url]
  (js/fetch url))

(defn use-json [response callback]
  (.then (.json response) callback))

(defn weather-api-url [city]
 (str "http://api.openweathermap.org/data/2.5/weather?q=" city "&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"))

(defn get-city-temperature-string [json]
  (let [temp (str (.-temp (.-main json)))
        city (str (.-name json))]
  (str city ": " temp "°C")))

(defn extract-data [response-promise callback] 
  (.then response-promise #(use-json %1 callback)))

(defn get-city-string [city] 
  (-> city 
    (weather-api-url)
    (fetch)
    (extract-data get-city-temperature-string)))


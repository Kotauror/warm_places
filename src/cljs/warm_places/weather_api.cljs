(ns warm_places.weather_api
  (:require [warm_places.general_api :refer [fetch
                                            use-json
                                            extract-data]]))

(enable-console-print!)

(defn weather-api-url [city]
 (str "http://api.openweathermap.org/data/2.5/weather?q=" city "&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"))

(defn get-city-temperature-string [city json]
  (if (not= (.-cod json) "404")
    (let [temp (str (.-temp (.-main json)))
          city (str (.-name json))]
    (str city ": " temp "°C"))
    city))

(defn null-fallback [city-with-temperature city]
  (if (not= city-with-temperature null)
    city-with-temperature 
    city))

(defn get-city-string [city] 
  (-> city 
    (weather-api-url)
    (fetch)
    (extract-data (partial get-city-temperature-string city))
    (null-fallback city)))


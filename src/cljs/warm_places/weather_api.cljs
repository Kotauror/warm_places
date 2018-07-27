(ns warm_places.weather_api
  (:require [warm_places.general_api :refer [fetch
                                            use-json
                                            extract-data]]))

(enable-console-print!)

(defn weather-api-url [city]
 (str "http://api.openweathermap.org/data/2.5/weather?q=" (:name city) "&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"))

(defn add-temp-to-city-hash [city json]
  (if (not= (.-cod json) "404")
    (let [temp (str (.-temp (.-main json)))
          city-name (str (.-name json))]
    (hash-map :name city-name :temp temp ))
    (hash-map :name (city :name) :temp "n/a")))

(defn get-city-temp-hash [city] 
  (-> city 
    (weather-api-url)
    (fetch)
    (extract-data (partial add-temp-to-city-hash city))))


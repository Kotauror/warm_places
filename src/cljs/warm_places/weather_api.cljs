(ns warm_places.weather_api
  (:require [warm_places.general_api :refer [fetch
                                            use-json
                                            extract-data]]))

(enable-console-print!)

(defn weather-api-url [city]
 (str "http://api.openweathermap.org/data/2.5/weather?q=" (:name city) "&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"))

(defn update-destination-with-json [city weather-json]
  (if (not= (.-cod weather-json) "404")
    (let [temp (str (.-temp (.-main weather-json)))
          city-name (str (.-name weather-json))]
    (hash-map :name city-name :temp temp ))
    (hash-map :name (city :name) :temp "n/a")))

(defn add-temperature-to-destination [city] 
  (-> city 
    (weather-api-url)
    (fetch)
    (extract-data (partial update-destination-with-json city))))


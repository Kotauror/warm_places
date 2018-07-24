(ns warm_places.weather_api)

(defn weather-api-url [city]
 (str "http://api.openweathermap.org/data/2.5/weather?q=" city "&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"))

(defn get-temperature [json]
 (str (.-temp (.-main json))))

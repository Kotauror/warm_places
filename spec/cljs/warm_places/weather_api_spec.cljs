(ns warm_places.weather_api-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.general_api :refer [fetch
                                            extract-data]]
            [warm_places.weather_api :refer [get-city-string
                                            weather-api-url
                                            get-city-temperature-string]]))
 (describe "weather-api-url"
    (it "builds a query string"
      (should=
      "http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"
        (weather-api-url "London"))))


(def response-json-weather
    (.parse js/JSON "{\"coord\":{\"lon\":-0.24,\"lat\":11.06},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":27.22,\"pressure\":993.48,\"humidity\":100,\"temp_min\":27.22,\"temp_max\":27.22,\"sea_level\":1022.78,\"grnd_level\":993.48},\"wind\":{\"speed\":3.23,\"deg\":229.002},\"clouds\":{\"all\":44},\"dt\":1532443459,\"sys\":{\"message\":0.0026,\"country\":\"GH\",\"sunrise\":1532411267,\"sunset\":1532456825},\"id\":2303287,\"name\":\"Bawku\",\"cod\":200}"))

(describe "get-city-temperature-string"
    (it "returns temperature from JSON"
      (should=
      "Bawku: 27.22°C"
      (get-city-temperature-string response-json-weather))))

(describe "get-city-string"
    (with-stubs)
    (it "calls the right methods"
    (with-redefs [
      weather-api-url (stub :weather-api-url-stub {:return :url})
      fetch (stub :fetch-stub {:return :response-promise})
      extract-data (stub :extract-data-stub)
    ]

    (get-city-string "Krakow")

    (should-have-invoked
      :weather-api-url-stub)

    (should-have-invoked
      :fetch-stub
      {:with
        [:url]})

    (should-have-invoked
      :extract-data-stub
      {:with
        [:response-promise get-city-temperature-string]}))))

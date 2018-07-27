(ns warm_places.weather_api-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.general_api :refer [fetch
                                            extract-data]]
            [warm_places.weather_api :refer [get-city-temp-hash
                                            weather-api-url
                                            add-temp-to-city-hash]]))
 (describe "weather-api-url"
    (it "builds a query string"
      (should=
      "http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=48e7a56793fa02078630b7e07b5342ad"
        (weather-api-url (hash-map :name "London")))))


(def response-json-weather
    (.parse js/JSON "{\"coord\":{\"lon\":-0.24,\"lat\":11.06},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":27.22,\"pressure\":993.48,\"humidity\":100,\"temp_min\":27.22,\"temp_max\":27.22,\"sea_level\":1022.78,\"grnd_level\":993.48},\"wind\":{\"speed\":3.23,\"deg\":229.002},\"clouds\":{\"all\":44},\"dt\":1532443459,\"sys\":{\"message\":0.0026,\"country\":\"GH\",\"sunrise\":1532411267,\"sunset\":1532456825},\"id\":2303287,\"name\":\"Bawku\",\"cod\":200}"))

(def response-json-with-404 
  (.parse js/JSON "{\"cod\":\"404\",\"message\":\"city not found\"}"))

(describe "add-temp-to-city-hash"
    (it "returns a string of city and temperature from JSON on successfull response"
      (should=
      {:name "Bawku" :temp "27.22"}
      (add-temp-to-city-hash (hash-map :name "Bawku") response-json-weather))))

(describe "add-temp-to-city-hash"
    (it "returns name of city on response status 404"
      (should=
      {:name "Bawku" :temp "n/a"}
      (add-temp-to-city-hash (hash-map :name "Bawku") response-json-with-404))))

(describe "get-city-string"
    (with-stubs)
    (it "calls the right methods"
    (with-redefs [
      weather-api-url (stub :weather-api-url-stub {:return :url})
      fetch (stub :fetch-stub {:return :response-promise})
      extract-data (stub :extract-data-stub {:return :city-with-temp})
    ]

    (get-city-temp-hash (hash-map :name "Krakow"))

    (should-have-invoked
      :weather-api-url-stub)

    (should-have-invoked
      :fetch-stub
      {:with
        [:url]})

    (should-have-invoked
      :extract-data-stub))))

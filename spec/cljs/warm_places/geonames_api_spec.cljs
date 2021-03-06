(ns warm_places.geonames_api-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.general_api :refer [fetch 
                                            extract-data
                                            combine-promises
                                            map-through-promise
                                            compose-functions]]
            [warm_places.geonames_api :refer [geonames-api-url
                                          call-geonames-api
                                          get-destinations
                                          add-temperatures-to-destinations
                                          handle-get-cities-click
                                          update-cities-from-json]]
            [warm_places.dom_manipulation :refer [update-cities-in-dom]]
            [warm_places.state :refer [update-cities-state
                                       add-to-cities
                                       get-cities
                                       reset-vector-atom
                                       cities]]))

(describe "geonames-api-url"
  (it "builds a query string"
    (should=
      "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=50.058144&lng=19.959547&cities=cities5000&radius=100&username=kotaur"
      (geonames-api-url 50.058144 19.959547 100))))

(def response-json-geonames
  (.parse js/JSON "{\"geonames\":[{\"toponymName\": \"Krakow\"}, {\"toponymName\": \"Warszawa\"}]}"))

(def response-json-weather
  (.parse js/JSON "{\"coord\":{\"lon\":-0.24,\"lat\":11.06},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":27.22,\"pressure\":993.48,\"humidity\":100,\"temp_min\":27.22,\"temp_max\":27.22,\"sea_level\":1022.78,\"grnd_level\":993.48},\"wind\":{\"speed\":3.23,\"deg\":229.002},\"clouds\":{\"all\":44},\"dt\":1532443459,\"sys\":{\"message\":0.0026,\"country\":\"GH\",\"sunrise\":1532411267,\"sunset\":1532456825},\"id\":2303287,\"name\":\"Bawku\",\"cod\":200}"))

(describe "get-destinations"
  (it "returns hash maps with city names"
    (should=
      [{:name "Krakow"} {:name "Warszawa"}]
      (get-destinations response-json-geonames))))

(describe "call-geonames-api"
  (with-stubs)
  (it "calls fetch with correct URL"
    (with-redefs [
      fetch (stub :fetch-stub {:return :response-promise})
      extract-data (stub :extract-data-stub)
    ]

      (call-geonames-api 100 200 50 :callback)

      (should-have-invoked
        :fetch-stub
        {:with
          ["http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=100&lng=200&cities=cities5000&radius=50&username=kotaur"]})))

    (it "calls extract-data with result of fetch and passed in callback"
      (with-redefs [
        fetch (stub :fetch-stub {:return :response-promise})
        extract-data (stub :extract-data-stub)
      ]

        (call-geonames-api 100 200 50 :callback)

        (should-have-invoked
          :extract-data-stub
          {:with
            [:response-promise :callback]}))))

(describe "handle-get-cities-click"
  (with-stubs)
  (it "calls call-geonames-api with given values and update-cities-from-json"
    (with-redefs [
      call-geonames-api (stub :call-geonames-api-stub)
      partial (stub :partial-stub {:return :partial-return})
      ]

      (handle-get-cities-click 100 200 50)

      (should-have-invoked
        :call-geonames-api-stub
        {:with
          [100 200 50 :partial-return]}))))

(describe "Update-cities-from-json"
  (with-stubs)
  (it "calls the right methods with right arguments"
    (with-redefs [
      get-destinations (stub :get-destinations-stub {:return :cities})
      add-temperatures-to-destinations (stub :add-temperatures-to-destinations-stub {:return :array-of-promises})
      combine-promises (stub :combine-promises-stub {:return :promises-from-all})
      map-through-promise (stub :map-through-promise-stub {:return :promise-from-one-function})
      compose-functions (stub :compose-functions-stub)
      reset-vector-atom (stub :reset-vector-atom-stub)
      ]

      (update-cities-from-json :reset-vector-atom-stub response-json-geonames)

      (should-have-invoked
        :get-destinations-stub
        {:with 
          [response-json-geonames]})

      (should-have-invoked
        :add-temperatures-to-destinations-stub
        {:with 
          [:cities]})

      (should-have-invoked
        :combine-promises-stub
        {:with 
          [:array-of-promises]})

      (should-have-invoked
        :map-through-promise-stub
        {:with 
          [:promises-from-all
           add-to-cities]})

      (should-have-invoked
        :compose-functions-stub
        {:with 
          [:promise-from-one-function
           update-cities-in-dom
             get-cities]}))))

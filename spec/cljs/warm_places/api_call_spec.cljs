(ns warm_places.api_call-
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.api_call :refer [api-url
                                          fetch
                                          extract-data
                                          call-api
                                          get-city-name
                                          get-city-names
                                          handle-get-cities-click
                                          update-cities-from-json]]
            [warm_places.dom_manipulation :refer [update-dom]]
            [warm_places.state :refer [update-cities-state
                                       cities]]))

(describe "Creating API query"
  (it "builds a query string"
    (should=
      "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=50.058144&lng=19.959547&cities=cities5000&radius=100&username=kotaur"
      (api-url 50.058144 19.959547 100))))

(def response-json
  (.parse js/JSON "{\"geonames\":[{\"toponymName\": \"Krakow\"}, {\"toponymName\": \"Warszawa\"}]}"))

(describe "Returns toponymName"
  (it "returns all the city names"
    (should=
      ["Krakow" "Warszawa"]
      (get-city-names response-json))))

(describe "call-api"
  (with-stubs)
  (it "calls fetch with correct URL"
    (with-redefs [
      fetch (stub :fetch-stub {:return :response-promise})
      extract-data (stub :extract-data-stub)
    ]

      (call-api 100 200 50 :callback)

      (should-have-invoked
        :fetch-stub
        {:with
          ["http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=100&lng=200&cities=cities5000&radius=50&username=kotaur"]})))

    (it "calls extract-data with result of fetch and passed in callback"
      (with-redefs [
        fetch (stub :fetch-stub {:return :response-promise})
        extract-data (stub :extract-data-stub)
      ]

        (call-api 100 200 50 :callback)

        (should-have-invoked
          :extract-data-stub
          {:with
            [:response-promise :callback]}))))

(describe "handle-get-cities-click"
  (with-stubs)
  (it "calls call-api with given values and update-cities-from-json"
    (with-redefs [
      call-api (stub :call-api-stub)
      ]

      (handle-get-cities-click 100 200 50)

      (should-have-invoked
        :call-api-stub
        {:with
          [100 200 50 update-cities-from-json]}))))

(describe "Update-cities-from-json"
  (with-stubs)
  (it "gets cities from JSON and updates cities atom"
    (with-redefs [
      update-dom (stub :update-dom-stub)
      ]

      (update-cities-from-json response-json)

      (should=
        ["Krakow" "Warszawa"]
        @cities))))

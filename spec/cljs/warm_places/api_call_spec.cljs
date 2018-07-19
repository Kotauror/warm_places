(ns warm_places.api_call-
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.api_call :refer [api-url
                                          fetch
                                          extract-data
                                          call-api
                                          get-city-name
                                          get-city-names
                                          update-list-of-cities]]
            [warm_places.state :refer [update-cities-state]]))

(describe "Creating API query"
  (it "builds a query string"
    (should=
      "http://api.geonames.org/findNearbyPlaceNameJSON?lat=50.058144&lng=19.959547&cities=cities1000&radius=100&username=kotaur"
      (api-url 50.058144 19.959547 100))))

(def response-json
  (.parse js/JSON "{\"geonames\":[{\"toponymName\": \"Krakow\"}, {\"toponymName\": \"Warszawa\"}]}"))

(describe "Returns toponymName"
  (it "returns all the city names"
    (should=
      ["Krakow" "Warszawa"]
      (get-city-names response-json))))

(describe "API call"
  (with-stubs)
  (it "calls fetch with correct URL"
    (with-redefs [
      fetch (stub :fetch-stub {:return :response-promise})
      extract-data (stub :extract-data-stub)
    ]

      (call-api 100 200 50)

      (should-have-invoked
        :fetch-stub
        {:with
          ["http://api.geonames.org/findNearbyPlaceNameJSON?lat=100&lng=200&cities=cities1000&radius=50&username=kotaur"]})))

    (it "calls extract-data with result of fetch and get-city-names function"
      (with-redefs [
        fetch (stub :fetch-stub {:return :response-promise})
        extract-data (stub :extract-data-stub)
      ]

        (call-api 100 200 50)

        (should-have-invoked
          :extract-data-stub
          {:with
            [:response-promise get-city-names]}))))

(describe "Update-list-of-cities"
  (with-stubs)
  (it "calls update-cities-state with return of call-api"
    (with-redefs [
      call-api (stub :call-api-stub {:return ["Krakow" "Warszawa"]})
      update-cities-state (stub :update-cities-state-stub)
      ]

      (update-list-of-cities 100 200 50)

      (should-have-invoked
        :update-cities-state-stub
        {:with
          [["Krakow" "Warszawa"]]}))))

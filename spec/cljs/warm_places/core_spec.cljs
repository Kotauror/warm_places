(ns warm_places.core-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [warm_places.core :refer [api-url
                                      get-city-names
                                      update-longitude
                                      update-latitude
                                      update-radius
                                      longitude
                                      latitude
                                      radius]]))

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

(describe "update atoms"
  (it "updates latitude atom"
   (update-latitude "10")
   (should=
    "10"
    @latitude))

  (it "updates longitude atom"
    (update-longitude "10")
    (should=
    "10"
    @longitude))

  (it "updates radius atom"
    (update-radius "10")
    (should=
    "10"
    @radius)))

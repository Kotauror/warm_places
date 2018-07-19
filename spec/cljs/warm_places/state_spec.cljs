(ns warm_places.state-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [warm_places.state :refer [update-longitude
                                      update-latitude
                                      update-radius
                                      update-cities-state
                                      longitude
                                      latitude
                                      radius
                                      cities]]))

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
    @radius))

  (it "updates cities atom"
    (update-cities-state ["Krakow" "Warszawa"])
    (should=
    ["Krakow" "Warszawa"]
    @cities)))

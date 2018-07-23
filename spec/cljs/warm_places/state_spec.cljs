(ns warm_places.state-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [warm_places.state :refer [update-longitude
                                      update-latitude
                                      update-radius
                                      update-cities-state
                                      add-to-wishlist
                                      longitude
                                      latitude
                                      radius
                                      cities
                                      wishlist]]))

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
    @cities))

  (it "adds element to a wishlist"
    (add-to-wishlist ["Warszawa"])
    (add-to-wishlist ["Krakow"])
    (should=
    ["Warszawa" "Krakow"]
    @wishlist))
)

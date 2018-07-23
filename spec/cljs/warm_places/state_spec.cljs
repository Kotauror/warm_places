(ns warm_places.state-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.state :refer [update-longitude
                                      update-latitude
                                      update-radius
                                      update-cities-state
                                      remove-city-from-cities
                                      handle-click-in-cities
                                      add-to-wishlist
                                      get-cities
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

  (it "removes element from cities atom"
    (update-cities-state ["Krakow" "Warszawa"])
    (remove-city-from-cities ["Krakow"])
    (should=
    ["Warszawa"]
    @cities)))

(describe "handle-click-in-cities" 
  (with-stubs) 
  (it "calls right methods" 
  (with-redefs [
    add-to-wishlist (stub :add-to-wishlist-stub)
    remove-city-from-cities (stub :remove-city-from-cities-stub)
  ]
  (update-cities-state ["London" "Paris"])

  (handle-click-in-cities "London")
  
  (should-have-invoked 
    :add-to-wishlist-stub
    :remove-city-from-cities-stub))))

(describe "get-cities" 
  (it "returns cities"

  (update-cities-state ["Krakow" "Warszawa"]) 

  (should= 
  ["Krakow" "Warszawa"]
  (get-cities))))

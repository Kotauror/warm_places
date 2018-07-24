(ns warm_places.state-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.state :refer [update-longitude
                                      update-latitude
                                      update-radius
                                      update-cities-state
                                      remove-element-from-atom
                                      handle-click-in-cities
                                      handle-click-in-wishlist
                                      reset-vector-atom
                                      add-to-wishlist
                                      get-cities
                                      get-wishlist
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
    (reset-vector-atom wishlist)
    (add-to-wishlist "Warszawa")
    (add-to-wishlist "Krakow")
    (should=
    ["Warszawa" "Krakow"]
    @wishlist))

  (it "removes element from atom"
    (update-cities-state ["Krakow" "Warszawa"])
    (remove-element-from-atom "Krakow" cities)
    (should=
    ["Warszawa"]
    @cities))

  (it "reset the value of vector atom to []"
    (update-cities-state ["Krakow" "Warszawa"])
    (reset-vector-atom cities)
    (should=
    []
    @cities)))

(describe "handle-click-in-cities" 
  (with-stubs) 
  (it "calls right methods" 
  (with-redefs [
    add-to-wishlist (stub :add-to-wishlist-stub)
    remove-element-from-atom (stub :remove-element-from-atom-stub)
  ]
  (update-cities-state ["London" "Paris"])

  (handle-click-in-cities "Krakow")
  
  (should-have-invoked 
    :add-to-wishlist-stub
    :remove-element-from-atom-stub))))

(describe "handle-click-in-wishlist" 
  (with-stubs) 
  (it "calls right methods" 
  (with-redefs [
    remove-element-from-atom (stub :remove-element-from-atom-stub)
  ]
  (update-cities-state ["London" "Paris"])
  (add-to-wishlist "London")

  (handle-click-in-wishlist "London")
  
  (should-have-invoked 
    :remove-element-from-atom-stub))))

(describe "get-cities" 
  (it "returns cities"

  (update-cities-state ["Krakow" "Warszawa"]) 

  (should= 
  ["Krakow" "Warszawa"]
  (get-cities))))

(describe "get-wihlist" 
  (it "returns wishlist"
  (reset-vector-atom wishlist)

  (add-to-wishlist "Krakow") 

  (should= 
  ["Krakow"]
  (get-wishlist))))


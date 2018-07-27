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
                                      add-to-cities
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
    (update-cities-state [(hash-map :name "Krakow") (hash-map :name "Warszawa")])
    (should=
    [{:name "Krakow"} {:name "Warszawa"}]
    @cities))

  (it "adds element to a wishlist"
    (reset-vector-atom wishlist)
    (add-to-wishlist (hash-map :name "Warszawa"))
    (add-to-wishlist (hash-map :name "Krakow"))
    (should=
    [{:name "Warszawa"} {:name "Krakow"}]
    @wishlist))

  (it "adds element to cities" 
    (reset-vector-atom cities)
    (add-to-cities (hash-map :name "Krakow"))
    (should=
    [{:name "Krakow"}]
    @cities))

  (it "removes element from atom"
    (update-cities-state [(hash-map :name "Krakow") (hash-map :name "Warszawa")])
    (remove-element-from-atom (hash-map :name "Krakow") cities)
    (should=
    [{:name "Warszawa"}]
    @cities))

  (it "reset the value of vector atom to []"
    (update-cities-state [(hash-map :name "Krakow") (hash-map :name "Warszawa")])
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
  (update-cities-state [(hash-map :name "London") (hash-map :name "Paris")])

  (handle-click-in-cities (hash-map :name "London"))
  
  (should-have-invoked 
    :add-to-wishlist-stub
    :remove-element-from-atom-stub))))

(describe "handle-click-in-wishlist" 
  (with-stubs) 
  (it "calls right methods" 
  (with-redefs [
    remove-element-from-atom (stub :remove-element-from-atom-stub)
  ]
  (update-cities-state [(hash-map :name "London") (hash-map :name "Paris")])
  (add-to-wishlist (hash-map :name "London"))

  (handle-click-in-wishlist (hash-map :name "London"))
  
  (should-have-invoked 
    :remove-element-from-atom-stub))))

(describe "get-cities" 
  (it "returns cities"

  (update-cities-state [(hash-map :name "Krakow") (hash-map :name "Warszawa")])

  (should= 
  [{:name "Krakow"} {:name "Warszawa"}]
  (get-cities))))

(describe "get-wihlist" 
  (it "returns wishlist"
  (reset-vector-atom wishlist)

  (add-to-wishlist (hash-map :name "Krakow"))

  (should= 
  [{:name "Krakow"}]
  (get-wishlist))))


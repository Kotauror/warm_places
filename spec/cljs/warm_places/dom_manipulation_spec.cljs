(ns warm_places.dom_manipulation-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
          [warm_places.dom_manipulation :refer [update-cities-in-dom
                                                  click-in-cities-wrapper
                                                  click-in-wishlist-wrapper]]
            [warm_places.html_elements :refer [cities-list
                                              wishlist-list]]
            [warm_places.state :refer [update-cities-state]]))

(describe "update-cities-in-dom"
  (it "puts list of cities to the container"
    (let [cities-list-element (.createElement js/document "ul")]
    (.setAttribute cities-list-element "id" cities-list)
    (.appendChild (.-body js/document) cities-list-element)

    (update-cities-in-dom [(hash-map :name "Cambridge" :temp "20ºC") (hash-map :name "London" :temp "25ºC")])

    (should=
      2
      (.-length (.querySelectorAll cities-list-element "li")))

    (should=
      "Cambridge: 20ºC"
      (.-textContent (.item (.querySelectorAll cities-list-element "li") 0))))))

(describe "click-in-cities-wrapper"
  (it "after click in cities shows the right number of cities in cities and wishlist"
    (let [cities-list-element (.createElement js/document "ul")
          wishlist-list-element (.createElement js/document "ul")]
    (.setAttribute cities-list-element "id" cities-list)
    (.setAttribute wishlist-list-element "id" wishlist-list)
    (.appendChild (.-body js/document) cities-list-element)
    (.appendChild (.-body js/document) wishlist-list-element)
    (update-cities-state (hash-map :name "Cambridge" :temp "20ºC"))

    (click-in-cities-wrapper (hash-map :name "Cambridge" :temp "20ºC"))
   
     (should=
       0
        (.-length (.querySelectorAll cities-list-element "li")))
     
    (should=
       1
        (.-length (.querySelectorAll wishlist-list-element "li"))))))

(describe "click-in-wishlist-wrapper"
  (it "after click in wishlist city disapperas from wishlist and doesnt come back to cities"
    (let [cities-list-element (.createElement js/document "ul")
          wishlist-list-element (.createElement js/document "ul")]
    (.setAttribute cities-list-element "id" cities-list)
    (.setAttribute wishlist-list-element "id"  wishlist-list)
    (.appendChild (.-body js/document) cities-list-element)
    (.appendChild (.-body js/document) wishlist-list-element)
    (update-cities-state (hash-map :name "Cambridge" :temp "20ºC"))

    (click-in-cities-wrapper (hash-map :name "Cambridge" :temp "20ºC"))
    (click-in-wishlist-wrapper (hash-map :name "Cambridge" :temp "20ºC"))
   
     (should=
       0
        (.-length (.querySelectorAll cities-list-element "li")))
     
    (should=
       0
        (.-length (.querySelectorAll wishlist-list-element "li"))))))




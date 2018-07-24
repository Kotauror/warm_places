(ns warm_places.dom_manipulation-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.dom_manipulation :refer [update-cities-in-dom
                                                  on-click-wrapper]]
            [warm_places.state :refer [get-cities
                                       handle-click-in-cities
                                       update-cities-state]]))

(describe "update-cities-in-dom"
  (it "puts list of cities to the container"
    (let [cities-list-element (.createElement js/document "ul")]
    (.setAttribute cities-list-element "id" "cities")
    (.appendChild (.-body js/document) cities-list-element)

    (update-cities-in-dom ["Cambridge" "London"])

    (should=
      2
      (.-length (.querySelectorAll cities-list-element "li")))

    (should=
      "Cambridge"
      (.-textContent (.item (.querySelectorAll cities-list-element "li") 0))))))

(describe "on-click-wrapper"
  (it "shows the right number of cities in cities list when clicked"
    (let [cities-list-element (.createElement js/document "ul")]
    (.setAttribute cities-list-element "id" "cities")
    (.appendChild (.-body js/document) cities-list-element)
    (update-cities-state ["Cambridge"])

    (on-click-wrapper "Cambridge")
   
     (should=
       0
        (.-length (.querySelectorAll cities-list-element "li")))))

;  (it "shows the right number of cities in wishlist list when clicked"
 ;   (let [wishlist-list-element (.createElement js/document "ul")]
  ;  (.setAttribute wishlist-list-element "id" "wishlist")
   ; (.appendChild (.-body js/document) wishlist-list-element))
   ; (update-cities-state ["Cambridge"])

    ;(on-click-wrapper "Cambridge")
   
     ;(should=
      ; 1
       ; (.-length (.querySelectorAll wishlist-list-element "li"))))


)





(ns warm_places.dom_manipulation-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.dom_manipulation :refer [update-cities-in-dom
                                                  on-click-wrapper]]
            [warm_places.state :refer [get-cities
                                       handle-click-in-cities]]))

(describe "update-cities-in-dom"
  (it "puts list of cities to the container"
    (let [cities-list-element (.createElement js/document "ul")]
    (.setAttribute cities-list-element "id" "cities")
    (.appendChild (.-body js/document) cities-list-element))

    (update-cities-in-dom ["Cambridge" "London"])

    (should=
      2
      (.-length (.querySelectorAll js/document "li")))

    (should=
      "Cambridge"
      (.-textContent (.item (.querySelectorAll js/document "li") 0)))))


  ; (it "decreases the number of cities when clicked"
  ;  (let [cities-list-element (.createElement js/document "ul")]
  ;  (.setAttribute cities-list-element "id" "cities")
  ;  (.appendChild (.-body js/document) cities-list-element))
  ;  (update-cities-in-dom ["Cambridge" "London"])
    
   ; (on-click-wrapper "Cambridge")

;    (should=
 ;     2
  ;    (.-length (.querySelectorAll js/document "li")))

   ;  (on-click-wrapper ["Cambridge"])

   ;  (should=
    ;  1
     ; (.-length (.querySelectorAll js/document "li")))))




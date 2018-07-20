(ns warm_places.dom_manipulation-spec
  (:require-macros [speclj.core :refer [describe it should= stub with-stubs should-have-invoked]])
  (:require [speclj.core]
            [warm_places.dom_manipulation :refer [update-dom]]))

(describe "update-dom-list"
  (it "puts list of cities to the container"

    (update-dom ["Cambridge" "London"])

    (should=
      2
      (.-length (.querySelectorAll js/document "li")))))

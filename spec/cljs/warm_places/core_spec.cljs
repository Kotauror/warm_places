(ns warm_places.core-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [warm_places.core :as my-core]))

(describe "A ClojureScript test"
  (it "One equals one"
    (should= 1 1)))

(describe "Counts average"
  (it "returns average"
    (should=
      15
      (my-core/average 20 10))))

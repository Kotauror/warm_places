(ns warm_places.core-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [warm_places.core :as my-core]))

(describe "Creating API query"
  (it "builds a query string"
    (should=
      "http://getnearbycities.geobytes.com/GetNearbyCities?radius=100&latitude=50.058144&longitude=19.959547"
      (my-core/api-url 50.058144 19.959547 100))))

(def json "{\"foo\": 1, \"bar\": 2, \"baz\": [1,2,3]}")

(describe "Returns first value"
  (it "returns first value"
    (should=
      "1"
      (my-core/return-first-value json))))

(ns warm_places.general_api)

(defn fetch [url]
  (js/fetch url))

(defn use-json [response callback]
  (.then (.json response) callback))

(defn extract-data [response-promise callback]
  (.then response-promise #(use-json %1 callback)))

(defn combine-promises [array-of-promises]
   (.all js/Promise array-of-promises))

(defn map-through-promise [promise f1]
  (.then promise #(mapv f1 %1)))

(defn resolve-and-call-two-functions [promise f1 f2]
  (.then promise #(f1 (f2))))

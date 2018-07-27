(ns warm_places.general_api)

(defn fetch [url]
  (js/fetch url))

(defn use-json [response callback]
  (.then (.json response) callback))

(defn extract-data [response-promise callback]
  (.then response-promise #(use-json %1 callback)))

(defn resolve-promises [array-of-promises]
   (.all js/Promise array-of-promises))

(defn resolve-and-call-one-function [promise f1 f2]
  (.then promise #(f1 f2 %1)))

(defn resolve-and-call-two-functions [promise f1 f2]
  (.then promise #(f1 (f2))))

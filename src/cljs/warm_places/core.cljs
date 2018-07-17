(ns warm_places.core
  (:require
 [clojure.string :refer [capitalize]]
 [domina :as dom]
 [domina.css :as css]
 [domina.events :as events]))

(enable-console-print!)

(defn average [a b]
(/ (+ a b) 2.0))

(defn add-listener[]
  (events/listen! (js/document.getElementById "submit") :click (fn [event]
    (let [latitude (.-value (.getElementById js/document "latitude"))
          longitude (.-value (.getElementById js/document "longitude"))
          radius (.-value (.getElementById js/document "radius"))]
    (print-to-console latitude longitude radius)
    (events/prevent-default event)))))

(defn print-to-console [latitude longitude radius]
  (js/console.log "Form was submitted")
  (js/console.log latitude)
  (js/console.log longitude)
  (js/console.log radius))

(set! (.-onload js/window) add-listener)

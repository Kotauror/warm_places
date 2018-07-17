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
  (events/listen! (dom/by-id "submit") :click (fn [evt]
    (let [latitude (dom/value (dom/by-id "latitude"))
          longitude (dom/value (dom/by-id "longitude"))
          radius (dom/value (dom/by-id "radius"))]
    (dom/log "Form was submitted")
    (dom/log latitude)
    (dom/log longitude)
    (dom/log radius))
    (events/prevent-default evt))))

(set! (.-onload js/window) add-listener)

(ns warm_places.core
  (:require
 [clojure.string :refer [capitalize]]
 [domina :as dom]
 [domina.css :as css]
 [domina.events :as events]))

(enable-console-print!)

(defn print-to-console [latitude longitude radius]
  (js/console.log "Form was submitted")
  (js/console.log latitude)
  (js/console.log longitude)
  (js/console.log radius))

(defn api-url [latitude longitude radius]
 (str "http://getnearbycities.geobytes.com/GetNearbyCities?radius=" radius "&latitude=" latitude "&longitude=" longitude))

(defn add-listener[]
  (events/listen! (js/document.getElementById "submit") :click (fn [event]
    (let [latitude (.-value (.getElementById js/document "latitude"))
          longitude (.-value (.getElementById js/document "longitude"))
          radius (.-value (.getElementById js/document "radius"))]
    (print-to-console latitude longitude radius)
    (events/prevent-default event)))))

(set! (.-onload js/window) add-listener)

(ns warm_places.core
  (:require [speclj.core]
            [warm_places.state :refer [update-longitude
                                      update-latitude
                                      update-radius]]))

(enable-console-print!)

(defn value-for-event [event] (.-value (.-target event)))

(defn wrap-function-for-event [handler-function]
  (fn [event] (handler-function (value-for-event event))))

(defn add-form-submit-listener []
  (.addEventListener
    (.getElementById js/document "submit")
    "click"
    (fn [] (console.log "proceed with API call "))))

(defn add-latitude-input-listener []
 (.addEventListener
  (.getElementById js/document "latitude")
  "change"
  (wrap-function-for-event update-latitude)))

(defn add-longitude-input-listener []
 (.addEventListener
  (.getElementById js/document "longitude")
  "change"
  (wrap-function-for-event update-longitude)))

(defn add-radius-input-listener []
  (.addEventListener
    (.getElementById js/document "radius")
    "change"
    (wrap-function-for-event update-radius)))

(set! (.-onload js/window) (fn []
                            (add-form-submit-listener)
                            (add-latitude-input-listener)
                            (add-longitude-input-listener)
                            (add-radius-input-listener)
))

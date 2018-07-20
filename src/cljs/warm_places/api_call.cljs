(ns warm_places.api_call
  (:require [warm_places.state :refer [update-cities-state]]))

(enable-console-print!)

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?lat=" latitude "&lng=" longitude "&cities=cities1000&radius=" radius "&username=kotaur"))

(defn- get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

(defn update-cities-from-json [json]
  (-> json
    (get-city-names)
    (update-cities-state)))

;untested
(defn fetch [url]
  (js/fetch url))

;untested
(defn use-json [response callback]
  (.then (.json response) callback))

;untested
(defn extract-data [response-promise callback]
  (.then response-promise (fn [response] (use-json response callback))))
  ; (.then response-promise #(use-json %1 callback))

(defn call-api [latitude longitude radius callback]
  (let [response-promise (fetch (api-url latitude longitude radius))]
  (extract-data response-promise callback)))

(defn handle-get-cities-click [latitude longitude radius]
 (call-api latitude longitude radius update-cities-from-json))

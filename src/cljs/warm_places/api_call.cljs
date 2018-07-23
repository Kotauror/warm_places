(ns warm_places.api_call
  (:require [warm_places.state :refer [update-cities-state
                                      cities]]
            [warm_places.dom_manipulation :refer [update-cities-in-dom]]))

(enable-console-print!)

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=" latitude "&lng=" longitude "&cities=cities5000&radius=" radius "&username=kotaur"))

(defn- get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (->> json
    (.-geonames)
    (mapv get-city-name)))

(defn update-cities-from-json [json]
  (-> json
    (get-city-names)
    (update-cities-state))
  (update-cities-in-dom @cities))

;untested
(defn fetch [url]
  (js/fetch url))

;untested
(defn use-json [response callback]
  (.then (.json response) callback))

;untested
(defn extract-data [response-promise callback]
  (.then response-promise #(use-json %1 callback)))

(defn call-api [latitude longitude radius callback]
  (-> (api-url latitude longitude radius)
    (fetch)
    (extract-data callback)))

(defn handle-get-cities-click [latitude longitude radius]
 (call-api latitude longitude radius update-cities-from-json))

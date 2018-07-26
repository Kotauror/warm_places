(ns warm_places.geonames_api
  (:require [warm_places.general_api :refer [fetch
                                            use-json
                                            extract-data]]
            [warm_places.state :refer [reset-vector-atom
                                      cities
                                      add-to-cities
                                      update-cities-state
                                      get-cities]]
            [warm_places.weather_api :refer [get-city-string]]
            [warm_places.dom_manipulation :refer [update-cities-in-dom]]))

(enable-console-print!)

(defn geonames-api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=" latitude "&lng=" longitude "&cities=cities5000&radius=" radius "&username=kotaur"))

(defn get-city-name [city-data]
 (.-toponymName city-data))

(defn get-city-names [json]
  (->> json
    (.-geonames)
    (mapv get-city-name)))

; untested - it is not possible to stub the Promise.all because promise supp    ort is not present in PhantomJS that is used by specljs.

(defn update-cities-from-json [json]
  (reset-vector-atom cities)
  (as-> json v
    (get-city-names v)
    (mapv get-city-string v)
    (.all js/Promise v)
    (.then v #(mapv add-to-cities %1))
    (.then v #(update-cities-in-dom (get-cities)))))

(defn call-geonames-api [latitude longitude radius callback]
  (-> (geonames-api-url latitude longitude radius)
    (fetch)
    (extract-data callback)))

(defn handle-get-cities-click [latitude longitude radius]
 (call-geonames-api latitude longitude radius update-cities-from-json))


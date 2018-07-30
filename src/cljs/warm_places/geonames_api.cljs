(ns warm_places.geonames_api
  (:require [warm_places.general_api :refer [fetch
                                            combine-promises
                                            map-through-promise
                                            compose-functions
                                            use-json
                                            extract-data]]
            [warm_places.state :refer [reset-vector-atom
                                      cities
                                      add-to-cities
                                      get-cities]]
            [warm_places.weather_api :refer [add-temperature-to-destination]]
            [warm_places.dom_manipulation :refer [update-cities-in-dom]]))

(enable-console-print!)

(defn geonames-api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=" latitude "&lng=" longitude "&cities=cities5000&radius=" radius "&username=kotaur"))

(defn build-destination [city-data]
  (hash-map :name (.-toponymName city-data)))

(defn get-destinations [geonames-json]
  (->> geonames-json
    (.-geonames)
    (mapv build-destination)))

(defn add-temperatures-to-destinations [cities]
  (mapv add-temperature-to-destination cities))

(defn update-cities-from-json [func-reseting-atom geonames-json]
  (func-reseting-atom cities)
  (as-> geonames-json v
    (get-destinations v)
    (add-temperatures-to-destinations v)
    (combine-promises v)
    (map-through-promise v add-to-cities)
    (compose-functions v update-cities-in-dom get-cities)))

(defn call-geonames-api [latitude longitude radius update-cities-from-json]
  (-> (geonames-api-url latitude longitude radius)
    (fetch)
    (extract-data update-cities-from-json)))

(defn handle-get-cities-click [latitude longitude radius]
 (call-geonames-api latitude longitude radius (partial update-cities-from-json reset-vector-atom)))


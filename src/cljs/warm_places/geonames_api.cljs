(ns warm_places.geonames_api
  (:require [warm_places.general_api :refer [fetch
                                            resolve-promises
                                            resolve-and-call-one-function
                                            resolve-and-call-two-functions
                                            use-json
                                            extract-data]]
            [warm_places.state :refer [reset-vector-atom
                                      cities
                                      add-to-cities
                                      get-cities]]
            [warm_places.weather_api :refer [get-city-string]]
            [warm_places.dom_manipulation :refer [update-cities-in-dom]]))

(enable-console-print!)

(defn geonames-api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?maxRows=20&lat=" latitude "&lng=" longitude "&cities=cities5000&radius=" radius "&username=kotaur"))

(defn get-city-name [city-data]
 (let [topo-name (.-toponymName city-data)]
  (hash-map :name topo-name)))

(defn get-city-names [json]
  (->> json
    (.-geonames)
    (mapv get-city-name)))

(defn get-cities-with-temperatures [cities]
  (mapv get-city-string cities))

(defn update-cities-from-json [json]
  (reset-vector-atom cities)
  (as-> json v
    (get-city-names v)
    (get-cities-with-temperatures v)
    (resolve-promises v)
    (resolve-and-call-one-function v mapv add-to-cities)
    (resolve-and-call-two-functions v update-cities-in-dom get-cities)))

(defn call-geonames-api [latitude longitude radius callback]
  (-> (geonames-api-url latitude longitude radius)
    (fetch)
    (extract-data callback)))

(defn handle-get-cities-click [latitude longitude radius]
 (call-geonames-api latitude longitude radius update-cities-from-json))


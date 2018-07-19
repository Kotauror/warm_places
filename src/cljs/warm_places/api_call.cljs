(ns warm_places.api_call)

(enable-console-print!)

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?lat=" latitude "&lng=" longitude "&cities=cities1000&radius=" radius "&username=kotaur"))

(defn- get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

;untested
(defn fetch [url]
  (.js/fetch url))

;untested
(defn extract-data [response-promise callback]
  (.then (.json (.then response-promise))) callback)

(defn call-api [latitude longitude radius]
  (extract-data
    (fetch (api-url latitude longitude radius))
    get-city-names)
  )

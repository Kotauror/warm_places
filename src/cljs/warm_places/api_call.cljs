(ns warm_places.api_call)

(enable-console-print!)

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?lat=" latitude "&lng=" longitude "&cities=cities1000&radius=" radius "&username=kotaur"))

(defn- get-city-name [city-data]
  (.-toponymName city-data))

(defn get-city-names [json]
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

; this function returns a json data structure PROMISE
; (defn fetch-data [latitude longitude radius]
;   (.json (.then (.js/fetch "http://api.geonames.org/findNearbyPlaceNameJSON?lat=50.058144&lng=19.959547&cities=cities1000&radius=100&username=kotaur"))))

(defn fetch [url]
  (.js/fetch url))

(defn call-api [latitude longitude radius]
  (fetch (api-url latitude longitude radius))
  )

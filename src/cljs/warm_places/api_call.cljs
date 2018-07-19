(ns warm_places.api_call)

(enable-console-print!)

(defn api-url [latitude longitude radius]
 (str "http://api.geonames.org/findNearbyPlaceNameJSON?lat=" latitude "&lng=" longitude "&cities=cities1000&radius=" radius "&username=kotaur"))

(defn- get-city-name [city-data]
  (js/console.log "in get-city-name")
  (js/console.log city-data)
  (.-toponymName city-data))

(defn get-city-names [json]
  (js/console.log "in get-city-names")
  (js/console.log json)
  (let [cities (.-geonames json)]
    (mapv get-city-name cities)))

;untested
(defn fetch [url]
  (js/fetch url))

(defn call-json [anything json-callback]
  (js/console.log "in call-json")
  (js/console.log anything)
  (.then (.json anything) json-callback))

;untested
(defn extract-data [response-promise callback]
  (js/console.log "in extract-data")
  (js/console.log response-promise)
  (.then response-promise #(call-json %1 callback)))

(defn call-api [latitude longitude radius]
  (js/console.log "in call-api")
  (js/console.log (str latitude longitude radius))
  (extract-data
    (fetch (api-url latitude longitude radius))
    get-city-names)
  )

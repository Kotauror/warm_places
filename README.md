# Warm places ClojureScript application 

## How to run it

// To run the project you need to install Clojure, Leiningen, and Speclj.

1. Clone the repo 
2. Build the project by running: `./clean; lein cljsbuild once`
3. See the tests by running: `./clean; lein cljsbuild test`

## What is it 

A ClojureScript application that process the GPS coordinates and radius of interest given by the user to show the cities within the radius with their temperatures.

Cities withing radius come from GeoNames API, weather comes from OpenWeather API. 






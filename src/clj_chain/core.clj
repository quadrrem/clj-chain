(ns clj-chain.core
  (:require [clj-chain.chain :refer :all]
           [org.httpkit.server :as http]
           [compojure.route :as route]
           [compojure.handler :as handler]
           [compojure.core :refer :all]
           [clojure.data.json :as json])
  (:gen-class))


(defn home [req]
  {:status  200
  :headers {"Content-Type" "text/html"}
  :body    "hello HTTP!"})

(defn blocks [req]
  {:status  200
  :headers {"Content-Type" "text/json"}
  :body    (json/write-str @chain)})

(defn mine [req]
  (let [data (:body req)]
    ))

(defroutes all-routes
  (GET "/" [] home)
  (GET "/blocks" [] blocks)
  (GET "/mine" [] mine)
  (route/not-found "<p>Page not found.</p>"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Starting server...")
  (http/run-server (handler/site #'all-routes) {:port 8080}))

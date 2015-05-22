(ns datum.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as server]
            [datum.service :as service]
            [immutant.web :refer [run]]))

;; This is an adapted service map, that can be started and stopped
;; From the REPL you can call server/start and server/stop on this service
(defonce runnable-service (server/create-server service/service))

(defn dev-service
  "Development config service"
  [& args]
  (-> service/service
      (merge {:env :dev
              ;; do not block thread that starts web server
              ::server/join? false
              ;; Routes can be a function that resolve routes,
              ;;  we can use this to set the routes to be reloadable
              ::server/routes #(deref #'service/routes)
              ;; all origins are allowed in dev mode
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}})
      ;; Wire up interceptor chains
      (server/default-interceptors)
      (server/dev-interceptors)
      (server/create-server)))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> (dev-service)
      (server/start)))

(defn start [options]
  (run (::server/servlet runnable-service) options))

(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (server/start runnable-service)
  ; (start {:path "/"})
  )


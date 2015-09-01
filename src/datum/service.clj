(ns datum.service
    (:require [io.pedestal.http :as bootstrap]
              [io.pedestal.http.route :as route]
              [io.pedestal.http.body-params :as body-params]
              [io.pedestal.http.route.definition :refer [defroutes]]
              [ring.util.response :as ring-resp]
              [datum.sri-scrape :as sri]
              [datum.ant-scrape :as ant]
              [clojure.stacktrace :as stack]))

(defn home-page
  [request]
  (ring-resp/response "Hello Immutant!"))

(defn info-contribuyente
  [{:keys [path-params] :as request}]
  (try 
    (ring-resp/response (sri/info-del-contribuyente (:ruc path-params)))
    (catch Exception e
      (stack/print-stack-trace e)
      (.printStackTrace e)
      {:body {:error "Contribuyente no encontrado"} :status 404})))

(defn find-vehicle
  [{:keys [path-params] :as request}]
  (try 
    (ring-resp/response (ant/find-vehicle (:plate path-params)))
    (catch Exception e
      (println e)
      {:body {:error "Vehículo no encontrado"} :status 404})))

(defroutes routes
  [[["/" {:get home-page}
     ^:interceptors [bootstrap/json-body]
     ["/contribuyentes/:ruc" {:get [:ruc info-contribuyente]}]
     ["/vehiculos/:plate" {:get [:vehicle find-vehicle]}]]]])

;; Consumed by datem.server/create-server
;; See bootstrap/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; :bootstrap/interceptors []
              ::bootstrap/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::bootstrap/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::bootstrap/resource-path "/public"

              ;; Either :jetty or :tomcat (see comments in project.clj
              ;; to enable Tomcat)
              ;;::bootstrap/host "localhost"
              ::bootstrap/type :jetty
              ::bootstrap/port (Integer. (or (System/getenv "PORT") 8080))})

(ns datum.ant-scrape
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as http]
            [datum.utils :refer :all])
  (:import [org.apache.commons.lang3.text WordUtils]))

(def ^:dynamic *base-url* "http://sistemaunico.ant.gob.ec:7021/PortalWEB/paginas/clientes/clp_grid_citaciones.jsp?")

(defn ensure-owner-info-length [coll]
  (if (< (count coll) 3)
    (concat coll [""])
    coll))

(defn owner-info [resource]
  (->> (map (comp clojure.string/trim html/text)
            (html/select
              resource
              #{[:body :> [:table html/first-of-type] [:tr html/first-child] :> [:td html/first-child]]
                [:body :> [:table html/first-of-type] (html/nth-child 2) :td]
                [:body :> [:table html/first-of-type] (html/nth-child 3) :td]}))
       (ensure-owner-info-length)
       (zipmap [:nombre :cedula :licencia])))

(defn plate-info [resource]
  (map (comp clojure.string/trim html/text)
       (html/select
         resource
         #{[:body :> [:table (html/nth-of-type 2)] [:tr html/first-child] :> :td.detalle_formulario]
           [:body :> [:table (html/nth-of-type 2)] [:tr (html/nth-child 2)] :> :td.detalle_formulario]
           [:body :> [:table (html/nth-of-type 2)] [:tr (html/nth-child 3)] :> :td.detalle_formulario]})))

(defn plate-query-resource [plate]
  (-> (fetch-url (str *base-url*
                      "ps_tipo_identificacion=PLA&ps_identificacion="
                      (clojure.string/upper-case plate) "&ps_placa="))
      :body
      (str-resource)))

(defn vehicle-info [plate]
  (let [resource (plate-query-resource plate)]
    (conj (plate-info resource)
          (owner-info resource))))

(defn parse-id [id]
  (get (clojure.string/split id #" \- ") 1 ""))

(defn find-vehicle [plate]
  (let [info (vehicle-info plate)
        vehicle (zipmap [:propietario :marca :color :anio_matricula :modelo
                         :clase :fecha_matricula :anio :servicio :fecha_caducidad]
                        info)]
    (-> vehicle
        (update-in [:propietario :cedula] parse-id)
        (update-in [:propietario :nombre] #(clojure.string/replace % #"\ " "")))))

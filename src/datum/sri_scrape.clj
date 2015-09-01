(ns datum.sri-scrape
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as http]
            [datum.utils :refer :all])
  (:import [org.apache.commons.lang3.text WordUtils]))

(def ^:dynamic *base-url* "https://declaraciones.sri.gob.ec/")

(defn company-info [resource]
  (map (comp clojure.string/trim html/text)
       (html/select
         resource
         #{[:#contenido :table html/first-child :td]
           [:#contenido :table (html/nth-child 6) :td]
           [:#contenido :table (html/nth-child 8) :td]
           [:#contenido :table (html/nth-child 10) :td]
           [:#contenido :table (html/nth-child 12) :td]
           [:#contenido :table (html/nth-child 14) :td]
           [:#contenido :table (html/nth-child 16) :td]})))

(defn parse-address [addr]
  (zipmap [:provincia :canton :calle]
          (clojure.string/split addr #" / ")))

(defn store-info [row-node]
  (-> (zipmap
        [:codigo :nombre_comercial :direccion :estado]
        (map (comp title-case clojure.string/trim html/text)
             (html/select
               row-node
               #{[:td.primeraCol]
                 [(html/nth-child 2)]
                 [(html/nth-child 3)]
                 [(html/nth-child 4)]})))
      (update-in [:direccion] parse-address)))

(defn additional-stores [resource]
  (map store-info
       (html/select (rest (html/select resource [:table.reporte]))
                         [[:tr (html/attr? :class)]])))

(defn main-store [resource]
  (store-info
       (concat
         (html/select resource [:form :> [:table (html/nth-of-type 2)]
                                [:tr html/last-child]])
         ; (html/select resource [:form :> [:table (html/nth-of-type 3)]
         ;                        [:tr (html/attr? :class)]])
         )))

(defn stores [resource]
  (map store-info
       (concat
         (html/select resource [:form :> [:table (html/nth-of-type 2)]
                                [:tr html/last-child]])
         (html/select resource [:form :> [:table (html/nth-of-type 3)]
                                [:tr (html/attr? :class)]]))))

(defn buscar-contribuyente [ruc]
  (let [info-resp (fetch-url (str *base-url*
                                  "facturacion-internet/consultas/publico/ruc-datos2.jspa?accion=siguiente&lineasPagina=1&ruc="
                                  ruc))
        info-resource (str-resource (:body info-resp))
        stores-resp (fetch-url (str *base-url*
                                    "facturacion-internet/consultas/publico/ruc-establec.jspa")
                               {:cookies {"JSESSIONID" (get-in info-resp [:cookies "JSESSIONID"])}})
        stores-resource (str-resource (:body stores-resp))]
    (conj (company-info info-resource)
          (main-store stores-resource)
          (into [] (stores stores-resource)))))

(defn info-del-contribuyente [ruc]
  (let [info (buscar-contribuyente ruc)
        contribuyente (zipmap [:establecimientos :matriz :razon_social :nombre_comercial
                               :estado :clase :tipo :obligado_contabilidad
                               :actividad_principal]
                              info)]
    (-> contribuyente
        (update-in [:obligado_contabilidad] = "SI")
        (update-in [:actividad_principal] clojure.string/capitalize)
        (update-in [:razon_social] title-case)
        (update-in [:nombre_comercial] title-case))))

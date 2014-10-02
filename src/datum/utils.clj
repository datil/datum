(ns datum.utils
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as http])
  (:import [org.apache.commons.lang3.text WordUtils]))

(defn title-case
  "Transforma a estilo título una cadena de caracteres. Considera el espacio en
   blanco, el punto y el guión como delimitadores."
  [string]
  (WordUtils/capitalizeFully string (char-array " .-/")))

(defn fetch-url [url & [opts method]]
  (let [rm (or method http/get)
        options (or opts {})
        response (apply rm [url options])]
    response))

(defn str-resource [str]
  (html/html-resource (java.io.StringReader. str)))
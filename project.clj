(defproject datum "0.2.0"
  :description "Datos para negocios del siglo XXI"
  :url "http://datum.co/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.pedestal/pedestal.service "0.4.1"]

                 ;; Remove this line and uncomment the next line to
                 ;; use Tomcat instead of Jetty:
                 [io.pedestal/pedestal.jetty "0.4.1"]
                 ;; [io.pedestal/pedestal.tomcat "0.3.0"]

                 [ch.qos.logback/logback-classic "1.1.2" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.7"]
                 [org.slf4j/jcl-over-slf4j "1.7.7"]
                 [org.slf4j/log4j-over-slf4j "1.7.7"]

                 ;; Libs
                 ; [org.immutant/web "2.0.0-alpha1"]
                 ;[org.immutant/immutant "2.x.incremental.290"]
                 ;  [org.immutant/immutant "2.1.5"
                 ;   :exclusions [ch.qos.logback/logback-classic
                 ;                ch.qos.logback/logback-core
                 ;                org.clojure/tools.reader
                 ;                org.clojure/java.classpath]]

                 [enlive "1.1.1"]
                 [clj-http "3.7.0"]
                 [org.apache.commons/commons-lang3 "3.1"]]
  :repositories [["Immutant 2.x incremental builds"
                  "http://downloads.immutant.org/incremental/"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "datum.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.3.0"]
                                  [proto-repl "0.3.1"]]}
             :uberjar {:aot :all}}
  :uberjar-name "datum.jar"
  :plugins [[lein-immutant "2.1.0"]]
  :main ^{:skip-aot true} datum.server)

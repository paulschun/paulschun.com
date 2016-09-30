(defproject twist-of-carts "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [stasis "2.3.0"]
                 [ring "1.4.0"]
                 [lein-ring "0.9.7"]
                 [hiccup "1.0.5"]
                 [optimus "0.19.0"]
                 [optimus-sass "0.0.3"]
                 [com.outpace/data.csv "0.1.3"]
                 [clj-http "3.3.0"]
                 [clj-time "0.12.0"]
                 [digest "1.4.5"]
                 [endophile "0.1.2"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler twist-of-carts.core/app}
  :aliases {"build-site" ["run" "-m" "twist-of-carts.core/export"]})

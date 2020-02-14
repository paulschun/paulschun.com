(defproject twist-of-carts "0.1.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [stasis "2.5.0"]
                 [ring "1.8.0"]
                 [lein-ring "0.9.7"]
                 [hiccup "1.0.5"]
                 [optimus "0.20.2"]
                 [clj-time "0.15.2"]
                 [digest "1.4.5"]
                 [endophile "0.2.1"]
                 [frontmatter "0.0.1"]
                 [garden "1.3.9"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-garden "0.3.0"]]
  :ring {:handler twist-of-carts.core/app}
  :aliases {"build-site" ["run" "-m" "twist-of-carts.core/export"]}
  :garden {:builds [{;; Optional name of the build:
                     :id "main"
                     ;; Source paths where the stylesheet source code is
                     :source-paths ["src"]
                     ;; The var containing your stylesheet:
                     :stylesheet twist-of-carts.styles/main
                     ;; Compiler flags passed to `garden.core/css`:
                     :compiler {;; Where to save the file:
                                :output-to "resources/public/css/main.css"
                                ;; Compress the output?
                                :pretty-print? false}}
                    {;; Optional name of the build:
                     :id "resume"
                     ;; Source paths where the stylesheet source code is
                     :source-paths ["src"]
                     ;; The var containing your stylesheet:
                     :stylesheet twist-of-carts.styles/resume
                     ;; Compiler flags passed to `garden.core/css`:
                     :compiler {;; Where to save the file:
                                :output-to "resources/public/css/resume.css"
                                ;; Compress the output?
                                :pretty-print? false}}]})

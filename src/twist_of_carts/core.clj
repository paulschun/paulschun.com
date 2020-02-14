(ns twist-of-carts.core
  (:require [ring.middleware.content-type :refer [wrap-content-type]]
            [stasis.core :as stasis]
            [optimus.prime :as optimus]
            [optimus.assets :as assets]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]
            [twist-of-carts.app :refer [get-pages]]))

(def target-dir
  "Where to tuck these files into"
  "target/build")

(defn get-assets []
  (assets/load-assets "public" ["/css/main.css"
                                "/css/resume.css"
                                "/js/main.js"
                                "/images/portrait.jpg"]))

(def app
  "Server"
  (-> (stasis/serve-pages get-pages)
      (optimus/wrap get-assets optimizations/all serve-live-assets)
      wrap-content-type))

(defn export
  "Exports files"
  []
  (let [assets (optimizations/all (get-assets) {})
        pages (get-pages)]
    ;(stasis/empty-directory! target-dir)
    (optimus.export/save-assets assets target-dir)
    (stasis/export-pages pages target-dir {:optimus-assets assets})))

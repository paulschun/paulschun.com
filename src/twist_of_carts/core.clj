(ns twist-of-carts.core
  (:require [ring.middleware.content-type :refer [wrap-content-type]]
            [stasis.core :as stasis]
            [twist-of-carts.app :refer [get-pages]]))

(def target-dir "" "target/build")

(def app (-> (stasis/serve-pages get-pages)
             wrap-content-type))

(defn export []
  (let [pages (get-pages)]
    (stasis/empty-directory! target-dir)
    (stasis/export-pages pages target-dir)))

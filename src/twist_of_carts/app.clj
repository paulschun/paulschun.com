(ns twist-of-carts.app
  (:require [twist-of-carts.views :as views]
            [clojure.data.csv :as csv]
            [clj-http.client :as client]))

(defn turn-csv-into-maps
  "Turns a CSV string into a list of maps."
  [csv-str]
  (let [parsed-csv (csv/read-csv csv-str)
        headers (map keyword (first parsed-csv))]
    (map (fn [row] (zipmap headers row)) (rest parsed-csv))))

(defn page-data-to-routes
  "Turn page data into routes."
  [page-data view]
  (reduce (fn [h pd] (merge h {(views/slug-path (:slug pd)) (view pd)})) {} page-data))

(defn blog-post-routes [blog-posts]
  (let [post-parts (partition 3 1 (concat [nil] blog-posts [nil]))]
    (reduce
     (fn [h parts]
       (let [post (nth parts 1)]
         (println post)
         (merge h {(views/slug-path (:slug post)) (apply views/blog-post parts)})))
     {}  post-parts)))

(defn get-pages
  "Get routes for this blog."
  []
  (let [blog-posts (turn-csv-into-maps (slurp file-loc))]
    (merge {"/" (views/home blog-posts)}
           (blog-post-routes blog-posts))))



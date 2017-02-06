 (ns twist-of-carts.app
  (:require [twist-of-carts.views :as views]
            [twist-of-carts.util :refer [slug-path]]
            [clojure.data.csv :as csv]
            [clj-http.client :as client]))

(def posts-loc
  ""
  "https://docs.google.com/spreadsheets/d/1zREszNeGUpdxSGCclWo_dWnfi0jr73cHVwyrQojwYJs/export?gid=1738936333&format=csv")

(def file-loc
  ""
  "resources/sumpin.csv")

(defn turn-csv-into-maps
  "Turns a CSV string into a list of maps."
  [csv-str]
  (let [parsed-csv (csv/read-csv csv-str)
        headers (map keyword (first parsed-csv))]
    (map (fn [row] (zipmap headers row)) (rest parsed-csv))))

(defn page-data-to-routes
  "Turn page data into routes."
  [page-data view]
  (reduce (fn [h pd] (merge h {(slug-path (:slug pd)) (view pd)})) {} page-data))

(defn blog-post-routes [blog-posts author-data]
  (let [post-parts (partition 3 1 (concat [nil] blog-posts [nil]))]
    (reduce
     (fn [h parts]
       (let [post (nth parts 1)]
         (merge h {(slug-path (:slug post)) (apply views/blog-post (concat parts [author-data]))})))
     {}  post-parts)))

(defn get-pages
  "Get routes for this blog."
  []
  (let [blog-posts (reverse (sort-by :posted_at (turn-csv-into-maps (slurp file-loc))))
        author-data (read-string (slurp "resources/author.edn"))
        resume-data (read-string (slurp "resources/resume.edn"))]
    (merge {"/" (views/home blog-posts author-data)
            "/resume/" (views/resume resume-data)
            "/cv/" (views/cv resume-data)}
           (blog-post-routes blog-posts author-data))))

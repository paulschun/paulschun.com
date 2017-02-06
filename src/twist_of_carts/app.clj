 (ns twist-of-carts.app
  (:require [twist-of-carts.util :refer [slug-path]]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [endophile.hiccup :refer [to-hiccup]]
            [frontmatter.core :as fm]
            [twist-of-carts.layout :refer [page-layout cv-layout]]
            [twist-of-carts.views.blog-post :as bp]
            [twist-of-carts.views.professional-info :as pi]
            [twist-of-carts.views.index :as i]))

(defn parse-permalink [file-loc]
  (last (str/split (first (str/split file-loc #"\.")) #"\-")))

(defn convert-blog-post [file]
  (let [fm-vars (fm/parse file)
        slug (parse-permalink (.getName file))]
    (merge (:frontmatter fm-vars)
           {:slug slug
            :body (:body fm-vars)})))

(def blog-posts
  (let [files (filter #(.endsWith (.getName %) ".markdown") (file-seq (io/file "resources/posts")))]
    (map #(convert-blog-post %) (reverse files))))

(defn blog-post-routes [posts author-data]
  (let [post-parts (partition 3 1 (concat [nil] posts [nil]))]
    (reduce
     (fn [h parts]
       (let [post (nth parts 1)]
         (merge h {(slug-path (:slug post))
                   (page-layout (apply bp/blog-post (concat parts [author-data])))})))
     {}  post-parts)))

(defn get-pages
  "Get routes for this blog."
  []
  (let [author-data (read-string (slurp "resources/author.edn"))
        resume-data (read-string (slurp "resources/resume.edn"))]
    (merge {"/" (page-layout (i/home blog-posts author-data))
            "/resume/" (cv-layout (pi/resume resume-data))
            "/cv/" (cv-layout (pi/cv resume-data))}
           (blog-post-routes blog-posts author-data))))

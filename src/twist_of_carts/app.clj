(ns twist-of-carts.app
  (:require [twist-of-carts.views :as views]
            [twist-of-carts.resume :as res]
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

(defn blog-post-routes [blog-posts author-data]
  (let [post-parts (partition 3 1 (concat [nil] blog-posts [nil]))]
    (reduce
     (fn [h parts]
       (let [post (nth parts 1)]
         (merge h {(views/slug-path (:slug post)) (apply views/blog-post (concat parts [author-data]))})))
     {}  post-parts)))

(defn get-pages
  "Get routes for this blog."
  []
  (let [blog-posts (reverse (sort-by :posted_at (turn-csv-into-maps (slurp file-loc))))
        author-data {:name "Paul S. Chun"
                     :email "paul@sixofhearts.us"
                     :description "Berlin-based software jack-of-all-trades. Building great software with great people. Formerly founded Rivalfox, previously worked at Heyzap."
                     :looking-for-projects? true}
        nav-data [{:name "Twitter"
                   :link "https://www.twitter.com/sixofhearts"}
                  {:name "LinkedIn"
                   :link "https://www.linkedin.com/in/paulschun"}
                  {:name "GitHub"
                   :link "https://github.com/sixofhearts"}
                  {:name "Email"
                   :link (str "mailto:" (:email author-data))}]]
    (merge {"/" (views/home blog-posts author-data nav-data)}
           (blog-post-routes blog-posts author-data)
           (res/get-pages))))



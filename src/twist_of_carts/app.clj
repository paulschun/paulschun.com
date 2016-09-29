(ns twist-of-carts.app
  (:require [twist-of-carts.views :as views]
            [clojure.data.csv :as csv]
            [clj-http.client :as client]))

(def posts-loc
  ""
  "")

(defn get-page-data [csv-location]
  (let [res (csv/read-csv (:body (client/get csv-location)))
        headers (map keyword (first res))]
    (map (fn [row] (zipmap headers row)) (rest res))))

(defn get-blog-posts []
  (reduce (fn [h a] (merge h {(str "/" (:slug a) "/") (views/blog-post a)})) {} (get-page-data posts-loc)))

(defn get-landing-pages []
  {})

(defn get-pages []
  (merge {"/" (views/home)}
         (get-blog-posts)
         (get-landing-pages)))

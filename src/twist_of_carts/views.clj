(ns twist-of-carts.views
  (:require [twist-of-carts.layout :refer [render-with-layout]]
            [twist-of-carts.views.blog-post :as bp]
            [twist-of-carts.views.index :as i]))

(defn home [blog-posts author-info]
  (render-with-layout (i/home blog-posts author-info)))

(defn blog-post [n post p a]
  (render-with-layout (bp/blog-post n post p a)))

(ns twist-of-carts.views
  (:require [twist-of-carts.layout :refer [page-layout cv-layout]]
            [twist-of-carts.views.blog-post :as bp]
            [twist-of-carts.views.professional-info :as pi]
            [twist-of-carts.views.index :as i]))

(defn home [blog-posts author-info]
  (page-layout (i/home blog-posts author-info)))

(defn blog-post [n post p a]
  (page-layout (bp/blog-post n post p a)))

(defn resume [r]
  (cv-layout (pi/resume r)))

(defn cv [r]
  (cv-layout (pi/cv r)))

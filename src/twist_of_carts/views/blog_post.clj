(ns twist-of-carts.views.blog-post
  (:require [clj-time.format :as f]
            [endophile.core :refer [mp]]
            [twist-of-carts.util :refer [long-date slug-path]]
            [endophile.hiccup :refer [to-hiccup]]))

(def long-date-formatter (f/formatter "EEEE, MMMM d, yyyy"))

(defn blog-post-content [{:keys [title posted_at location body]}]
  [:div
   [:h1 title]
   [:div {:class "metadata"}
    [:div (long-date posted_at)]
    [:div location]]
   [:div {:class "body"} (to-hiccup (mp body))]])

(defn breadcrumb [{:keys [name description]}]
  [:div
   [:p "Author: " name]
   [:p description]])

(defn post-link [post]
  [:a {:href (slug-path (:slug post))} (:title post)])

(defn pagination [next-post prev-post]
  [:div
   (if next-post [:div "Newer Post: " (post-link next-post)])
   (if prev-post [:div "Older Post: " (post-link prev-post)])
   [:div [:a {:href "/"} "Home"]]])

(defn blog-post
  "The layout for a blog post."
  [next-post post prev-post author-info]
  {:title (str (:name author-info) " &mdash; " (:title post))
   :content [:div {:class "blog-post"}
             (blog-post-content post)
             [:div {:class "boilerplate"}
              (breadcrumb author-info)
              (pagination next-post prev-post)]]})

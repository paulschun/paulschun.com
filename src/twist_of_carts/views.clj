(ns twist-of-carts.views
  (:require [ring.middleware.content-type :refer [wrap-content-type]]
            [stasis.core :as stasis]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn render-with-layout [content]
  (html5
   {:lang "en"}
   [:head
    (include-css "/mystyle.css")]
   [:body
    [:div content]]))

(defn blog-post [{:keys [title posted_at location content]}]
  (render-with-layout
   [:div
    [:h1 title]
    [:div content]
    [:div (str "Posted " posted_at ", " location)]]))

(defn home [] (render-with-layout "Hi"))

(defn landing-page [data])

(ns twist-of-carts.views
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.coerce :as c]
            [digest :as digest]
            [endophile.core :refer [mp]]
            [endophile.hiccup :refer [to-hiccup]]))

(def custom-formatter (f/formatter "EEEE, MMMM d, yyyy"))

(def short-date (f/formatter "MMM d, yyyy"))

(defn google-analytics
  ""
  []
  [:script
   "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
      ga('create', 'UA-67228281-1', 'auto');
      ga('send', 'pageview');"])

(defn slug-path [slug] (str "/" slug "/"))

(defn timestamp-to-string [ts] (f/unparse short-date (c/from-long (* 1000 (Long/parseLong ts)))))

(defn render-with-layout
  "Render a page with the shared layout."
  [title content]
  (html5
   {:lang "en"}
   [:head
    (google-analytics)
    [:title title]
    (include-css "https://fonts.googleapis.com/css?family=Catamaran:200")
    (include-css "/css/main.css")]
   [:body
    [:div {:class "container"} content]]))

(defn gravatar-url [email]
  (str "https://www.gravatar.com/avatar/" (digest/md5 email) "?s=128&d=mm"))

(defn blog-post
  "The layout for a blog post."
  [next-post
   {:keys [title posted_at location content]}
   prev-post
   {:keys [name description]}]
  (render-with-layout (str name " &mdash; " title)
   [:div {:class "blog-post"}
    [:h1 title]
    [:div {:class "metadata"}
     [:div (timestamp-to-string posted_at)]
     [:div location]]
    [:div {:class "body"} (to-hiccup (mp content))]
    [:div {:class "boilerplate"}
     [:p "Author: " name]
     [:p description]
         (if next-post
      [:div
       "Newer Post: "
       [:a {:href (slug-path (:slug next-post))} (:title next-post)]])
    (if prev-post
      [:div
       "Older Post: "
       [:a {:href (slug-path (:slug prev-post))} (:title prev-post)]])
     [:div [:a {:href "/"} "Home"]]]]))

(defn blog-post-link
  "Links on the homepage to the actual blog post."
  [{:keys [slug title posted_at]}]
  [:div
   [:span {:class "blog-list-date"} (str(timestamp-to-string posted_at) " &mdash; ")]
   [:a {:href (slug-path slug)} title]])

(defn home
  "The homepage."
  [blog-posts
   {:keys [name email description looking-for-projects?]}
   nav-data]
  (render-with-layout name
   [:div {:class "homepage"}
    [:header
     [:h1 name]
     [:img {:src (gravatar-url email)}]
     [:p description]
     [:p
      "I currently "
      (if looking-for-projects?
        [:span {:class "looking-for"} "am"]
        [:span {:class "not looking-for"} "am not"])
      " accepting proposals for new projects."]]
    [:nav
     [:ul {:class "fixed-nav"}
      [:li
       [:a {:href "/resume"} "R&#0233;sum&#0233;"]
       [:span " / "]
       [:a {:href "/cv"} "CV"]]
      (map
       (fn [{:keys [name link]}] [:li [:a {:href link :target "_blank"} name]])
       nav-data)]
     [:ul {:class "blog-nav"}
      (map (fn [bp] [:li (blog-post-link bp)]) blog-posts)]]
    ]))

(defn landing-page
  "The layout for a landing page."
  [data]
  "Hi.")

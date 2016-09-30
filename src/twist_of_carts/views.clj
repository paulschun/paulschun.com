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

(defn slug-path [slug] (str "/" slug "/"))

(defn timestamp-to-string [ts] (f/unparse short-date (c/from-long (* 1000 (Long/parseLong ts)))))

(defn render-with-layout
  "Render a page with the shared layout."
  [content]
  (html5
   {:lang "en"}
   [:head
    (include-css "https://fonts.googleapis.com/css?family=Catamaran:200")
    (include-css "/css/main.css")]
   [:body
    [:div {:class "container"} content]]))

(defn gravatar-url [email]
  (str "https://www.gravatar.com/avatar/" (digest/md5 email) "?s=128&d=mm"))

(defn blog-post
  "The layout for a blog post."
  [next-post {:keys [title posted_at location content]} prev-post]
  (render-with-layout
   [:div {:class "blog-post"}
    [:h1 title]
    [:div {:class "metadata"}
     [:div (timestamp-to-string posted_at)]
     [:div location]
     [:div "Paul S. Chun"]]
    [:div {:class "body"} (to-hiccup (mp content))]
    (if next-post
      [:div
       "Next Post: "
       [:a {:href (slug-path (:slug next-post))} (:title next-post)]])
    (if prev-post
      [:div
       "Previous Post: "
       [:a {:href (slug-path (:slug prev-post))} (:title prev-post)]])
    [:div [:a {:href "/"} "Go Back Home"]]]))

(defn blog-post-link
  "Links on the homepage to the actual blog post."
  [{:keys [slug title posted_at]}]
  [:div
   [:span {:class "blog-list-date"} (str(timestamp-to-string posted_at) " &mdash; ")]
   [:a {:href (slug-path slug)} title]])

(defn home
  "The homepage."
  [blog-posts]
  (render-with-layout
   [:div {:class "homepage"}
    [:header
     [:h1 "Paul S. Chun"]
     [:img {:src (gravatar-url "paul@sixofhearts.us")}]
     [:p "Berlin-based software jack-of-all-trades. Building great software with great people. Formerly founded Rivalfox, previously worked at Heyzap."]
     [:p
      "I currently "
      [:span {:class "not looking-for"} "am not"]
      " accepting proposals for new projects."]]
    [:nav
     [:ul {:class "fixed-nav"}
      [:li
       [:a {:href "/resume"} "Résumé"]
       [:span " / "]
       [:a {:href "/cv"} "CV"]]
      [:li [:a {:href "https://www.twitter.com/sixofhearts"} "Twitter"]]
      [:li [:a {:href "https://www.linkedin.com/in/paulschun"} "LinkedIn"]]
      [:li [:a {:href "https://github.com/sixofhearts"} "GitHub"]]
      [:li [:a {:href "mailto:paul@sixofhearts.us"} "Email"]]]
     [:ul {:class "blog-nav"}
      (map (fn [bp] [:li (blog-post-link bp)]) blog-posts)]]
    ]))

(defn landing-page
  "The layout for a landing page."
  [data]
  "Hi.")

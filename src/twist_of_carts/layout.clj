(ns twist-of-carts.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

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

(defn page-layout
  "Render a page with the shared layout."
  [{title :title, content :content}]
  (html5
   {:lang "en"}
   [:head
    (google-analytics)
    [:title title]
    (include-css "https://fonts.googleapis.com/css?family=Catamaran:200")
    (include-css "/css/main.css")]
   [:body
    [:div {:class "container"} content]]))

(defn cv-layout
  "Render a page with the shared layout."
  [{title :title, content :content}]
  (html5
   [:head
    [:title title]
    (google-analytics)
    (include-css "/css/resume.css")]
   [:body
    [:div {:class "container"} content]]))

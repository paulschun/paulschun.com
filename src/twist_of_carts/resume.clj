(ns twist-of-carts.resume
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [clj-time.coerce :as c]
            [clojure.edn :as edn]
            [clojure.string :as str]
            [twist-of-carts.layout :refer [google-analytics]]
            [digest :as digest]))

(defn sec-item [{:keys [header subheader items subnote
                         metadata-header metadata-subheader]}]
  [:div
   [:h3 header]
   [:div {:class "details level-2"}
    [:div {:class "field"} subheader]
    [:div {:class "secondary"}
     (if subnote subnote)
     (if items [:ul {:class "bullets"} (map (fn [item] [:li item]) items)])]]
   [:div {:class "metadata"} metadata-header [:br] metadata-subheader]])

(defn sublist-item [{:keys [name values]}]
  [:div
   [:div {:class "field"} name]
   [:span (str/join ", " values)]])

(defn list-item [{:keys [header sublists]}]
  [:div
   [:h3 header]
   [:div {:class "level-2"}
    (map sublist-item sublists)]])

(defn build-sections [sections]
  (map
   (fn [{:keys [name items lists]}]
     [:div [:h2 name]
      (if items
        [:ul {:class "level-1"}
         (map (fn [item] [:li {:class "clearfix"} (sec-item item)]) items)])
      (if lists
        [:div {:class "level-1"} (map list-item lists)])])
   sections))

(def resume-data (edn/read-string (slurp "resources/resume.edn")))

(defn render-with-layout [title body]
  (html5
   [:head
    [:title title]
    (google-analytics)
    (include-css "/css/resume.css")]
   [:body
    [:div {:class "container"} body]]))

(defn resume
  ""
  []
  (let [{:keys [bio sections]} resume-data]
    (render-with-layout
     (str (:name bio) " &mdash; R&#0233;sum&#0233;")
     [:div
      [:div {:class "headline clearfix"}
       [:h1 (:name bio)]]
      (build-sections sections)])))

(defn cv
  ""
  []
  (let [{:keys [bio sections]} resume-data]
    (render-with-layout
     (str (:name bio) " &mdash; Curriculum Vitae")
     [:div
      [:div {:class "personal-information clearfix"}
       [:h1 (:name bio)]
       [:div {:class "portrait"}
        [:img {:src (:portrait bio)}]]
       [:h2 "Personal Information"]
       [:div {:class "contact-info"}
        [:ul {:class "bullets"} (map (fn [item] [:li item]) (:lines bio))]]
       ]
      (build-sections sections)])))

(defn get-pages [] {"/resume/" (resume), "/cv/" (cv)})

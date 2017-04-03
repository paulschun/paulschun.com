(ns twist-of-carts.views.index
  (:require [digest :as digest]
            [twist-of-carts.util :refer [short-date slug-path]]))

(defn job-status [looking-for-projects?]
  [:p
   "I currently "
   (if looking-for-projects?
     [:span {:class "looking-for"} "am"]
     [:span {:class "not looking-for"} "am not"])
   " accepting proposals for new projects."])

(defn gravatar-url [email]
  (str "https://www.gravatar.com/avatar/" (digest/md5 email) "?s=128&d=mm"))

(defn blog-post-link
  "Links on the homepage to the actual blog post."
  [{:keys [slug title posted_at]}]
  [:div
   [:span {:class "blog-list-date"} (str (short-date posted_at) " &mdash; ")]
   [:a {:href (slug-path slug)} title]])

(defn fixed-nav-item [title url & popout?]
  (let [a-opts (if popout? {:target "_blank"} {})]
    [:li [:a (merge a-opts {:href url}) title]]))

(defn fixed-nav [{:keys [twitter-handle linkedin-id github-id email]}]
  [:ul {:class "fixed-nav"}
   [:li
    [:a {:href "/resume"} "R&#0233;sum&#0233;"]
    [:span " / "]
    [:a {:href "/cv"} "CV"]]
   (fixed-nav-item "Twitter" (str "https://www.twitter.com/" twitter-handle) true)
   (fixed-nav-item "LinkedIn" (str "https://www.linkedin.com/in/" linkedin-id) true)
   (fixed-nav-item "GitHub" (str "https://github.com/" github-id) true)
   (fixed-nav-item "Write Honey" "https://writehoney.com/" true)
   (fixed-nav-item "Email" (str "mailto:" email))])

(defn blog-nav [blog-posts]
  [:ul {:class "blog-nav"}
   (map (fn [bp] [:li (blog-post-link bp)]) blog-posts)])

(defn home
  "The homepage."
  [blog-posts {:keys [name email description looking?] :as author-info}]
  {:title name
   :content [:div {:class "homepage"}
             [:header
              [:h1 name]
              [:img {:src (gravatar-url email)}]
              [:p description]
              (job-status looking?)]
             [:nav
              (fixed-nav author-info)
              (blog-nav blog-posts)]]})

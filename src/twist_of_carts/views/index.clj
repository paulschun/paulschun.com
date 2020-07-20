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
  (str "https://www.gravatar.com/avatar/" (digest/md5 email) "?s=320&d=mm"))

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
   (fixed-nav-item "Write Honey" "https://writehoney.com/" true)])

(defn blog-nav [blog-posts]
  [:ul {:class "blog-nav"}
   (map (fn [bp] [:li (blog-post-link bp)]) blog-posts)])

(defn home
  "The homepage."
  [blog-posts {:keys [name email description looking?] :as author-info}]
  {:title "Paul S. Chun &mdash; Let's Build It."
   :content [:div
             [:section.hero.is-bold.is-medium
              [:div.hero-body
               [:div.container
                [:img {:src (gravatar-url email)}]
                [:h1.title "Paul S. Chun"]
                [:h2.subtitle "Founder and CEO of "
                 [:a {:href "https://elbower.com/"} "Elbower"]]
                [:h2.subtitle "Founder and CEO of I create, work on and advise startups that care about making useful and rock-solid products."]
                ]]]
             ]})

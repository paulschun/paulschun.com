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
    (include-css "https://cdnjs.cloudflare.com/ajax/libs/bulma/0.8.0/css/bulma.min.css")
    (include-css "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.min.css")
    (include-css "/css/main.css")]
   [:body
    [:nav.navbar.psc-item
     [:div.navbar-brand
      [:a.navbar-item {:href "/"} "Paul S. Chun"]
      [:a.navbar-burger {:role "button"
                         :aria-label "menu"
                         :aria-expanded false
                         :data-target "navMenu"}
       (take 3 (repeat [:span {:aria-hidden true}]))]]
     [:div#hireMeModal.modal
      [:header.modal-background]
      [:section.modal-card
       [:div.modal-card-head
        [:p.modal-card-title "Hire Me"]
        [:button.delete.modal-close-action {:aria-label "close"}]]
       [:div.modal-card-body
        [:div.notification.is-danger
         "Please note that, due to current commitments and time constraints, it will be difficult for me to take on new projects at this time."]
        [:div.content
         [:p "Thank you for considering me for your endeavor! I am eager to learn more about how I can bring my experience, knowledge and enthusiasm to help bring you short-term and long-term success."]]]
       [:footer.modal-card-foot
        [:a.button.is-success {:href "mailto:paul@sixofhearts.us"}
         "Send Me a Proposal"]
        [:button.button.modal-close-action "Close"]]]
      ]
     [:div#navMenu.navbar-menu
      [:div.navbar-start
       ;; [:a.navbar-item {:href "/blog"} "Blog"]
       [:div.navbar-item.has-dropdown.is-hoverable
        [:span.navbar-link "Professional Information"]
        [:div.navbar-dropdown
         [:a.navbar-item {:href "/resume/"} "R&#0233;sum&#0233;"]
         [:a.navbar-item {:href "/cv/"} "Curriculum Vitae"]]]
       [:div.navbar-item.has-dropdown.is-hoverable
        [:span.navbar-link {:href "/"} "Contact"]
        [:div.navbar-dropdown
         [:a.navbar-item {:href "mailto:paul@sixofhearts.us"}
          [:i.fa.fa-envelope]
          [:span "&nbsp;Email"]]
         [:a.navbar-item {:href "https://www.twitter.com/paulschun" :target "_blank"}
          [:i.fab.fa-twitter]
          [:span "&nbsp;Twitter"]]
         [:a.navbar-item {:href "https://www.linkedin.com/in/paulschun" :target "_blank"}
          [:i.fab.fa-linkedin]
          [:span "&nbsp;LinkedIn"]]
         [:a.navbar-item {:href "https://angel.co/paulschun" :target "_blank"}
          [:i.fab.fa-angellist]
          [:span "&nbsp;Angellist"]]
         [:a.navbar-item {:href "https://www.github.com/sixofhearts" :target "_blank"}
          [:i.fab.fa-github]
          [:span "&nbsp;GitHub"]]
        ]]]
      [:div.navbar-end
       [:div.navbar-item
        [:span.button.hire-me {:data-target "hireMeModal"}
         [:i.fas.fa-handshake]
         "&nbsp;Hire Me"]]
       [:a.navbar-item {:href "mailto:paul@sixofhearts.us"}
        [:span.button.is-primary
         [:i.fa.fa-envelope]
         "&nbsp;Email Me"]]]]]
    [:div content]
    (include-js "/js/main.js")
    ]))

(defn cv-layout
  "Render a page with the shared layout."
  [{title :title, content :content}]
  (html5
   [:head
    [:title title]
    (google-analytics)
    (include-css "/css/resume.css")]
   [:body
    [:div.container content]]))

(ns twist-of-carts.util
  (:require [clj-time.format :as f]
            [clj-time.coerce :as c]))

;; URLs

(defn slug-path [slug]
  (str "/" slug "/"))

;; Time

(def custom-formatters
  {:long "EEEE, MMMM d, yyyy"
   :short "MMM d, yyyy"})

(defn ts-to-date-format [ts date-format]
  (let [formatter (f/formatter (date-format custom-formatters))
        time (f/parse (f/formatters :date-time-no-ms) ts)]
    (f/unparse formatter time)))

(defn long-date [ts]
  (ts-to-date-format ts :long))

(defn short-date [ts]
  (ts-to-date-format ts :short))

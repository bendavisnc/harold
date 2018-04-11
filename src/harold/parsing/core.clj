(ns harold.parsing.core
  (:refer-clojure :exclude [time])
  (:require [clj-time.core :as t]
            [clj-time.format :as t-format]))

(def ^:private price-regex #"\$(\d*)")

;; The time format that craigslist uses.
(def ^:private time-format (t-format/formatter "yyyy-MM-dd HH:mm"))

(defn- get-attribute [e, selector, attr]
  (.get (.attributes (first (.select e selector)))
        attr))




(defn description [e]
  (.html (.select e "a.result-title")))

(defn price [e]
  (when-let [price-text (last (re-find price-regex
                                       (.html (.select e "span.result-price"))))]
    (Integer/parseInt price-text)))

(defn time [e]
  (t-format/parse time-format (.get (.attributes (first (.select e "time.result-date")))
                               "datetime")))

(defn url [e]
  (get-attribute e "a.result-image", "href"))

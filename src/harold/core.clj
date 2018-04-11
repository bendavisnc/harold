(ns harold.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [harold.parsing.core :as parsing]
            [harold.model.item-info :as item-info]
            [harold.filtering.basic :as basic-filter]
            [harold.filtering.relevant :as relevance-filter]
            [clj-time.core :as t]
            [clj-time.format :as t-format]
            [orchestra.spec.test :as orchestra]
            [clojure.tools.logging :as log]
            [harold.constants :as constants]
            [clojure.spec.alpha :as spec]
            [harold.utils :as utils]
            [harold.services.email :as email]
            [harold.services.persistence :as persistence])
  (:import [org.jsoup Jsoup]
           (org.joda.time DateTime)))

(orchestra/instrument)

(defn fetch-document [url]
  (-> url
      (Jsoup/connect)
      (.get)))

(defn get-result-rows [doc]
  (.select doc "li.result-row"))

(defn parse-result-rows [rows]
  (map (fn [e]
         (item-info/create :description (parsing/description e)
                           :time (parsing/time e)
                           :price (parsing/price e)
                           :url (parsing/url e)))
       rows))

(defn- get-sleep-time
  "Returns the time to sleep (milliseconds (long)) until the next `inquiry`."
  [last-run, update-period]
  (let [now (t/now)
        millis-since-last-run (- (.getMillis now)
                                 (.getMillis last-run))]
    (- update-period millis-since-last-run)))

(spec/fdef get-sleep-time :args (spec/cat :last-run #(instance? DateTime %)
                                          :update-period pos-int?)
                          :ret pos-int?)

(defn perform-new-inquiry! [base-data, runtime-data]
  (log/info "Performing new inquiry!")
  (let [retrieved-data (->> base-data
                            :url
                            (fetch-document)
                            (get-result-rows)
                            (parse-result-rows))
        pre-filtered-data (basic-filter/filter base-data retrieved-data)
        complete-filtered-data (relevance-filter/filter runtime-data pre-filtered-data)]
    (do
      (email/email complete-filtered-data)
      (persistence/update-runtime-data! complete-filtered-data))))

(defn main-loop []
  (log/info "Starting main loop.")
  (let [base-data (utils/get-base-data)
        update-period (:update-period base-data)]
    (loop []
      (let [runtime-data (utils/get-runtime-data)
            last-run (utils/get-last-run runtime-data)]
        (if (or (nil? last-run)
                (t/after? (t/now) last-run))
            (perform-new-inquiry! base-data, runtime-data)
            ;else
            (Thread/sleep (get-sleep-time last-run, update-period)))
        (recur)))))

(defn -main
  [& args]
  (main-loop))


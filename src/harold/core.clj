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
            ;[clojure.tools.logging :as log]
            [harold.constants :as constants]
            [clojure.spec.alpha :as spec]
            [harold.utils :as utils]
            [harold.services.email :as email]
            [harold.persistence.core :as persistence])
  (:import [org.jsoup Jsoup]
           (org.joda.time DateTime)))

(orchestra/instrument)

(defn fetch-document [url]
  (if (.contains url "https://")
    (-> url
        (Jsoup/connect)
        (.get))
    ;else (assume testing)
    (Jsoup/parse (slurp (io/resource url)))))


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
  "Returns the time to sleep (milliseconds (long)) until the next `inquiry`.
   Returns nil if there is no time to wait."
  [last-run, update-period]
  (when last-run
    (let [now (t/now)
          millis-since-last-run (- (.getMillis now)
                                   (.getMillis last-run))
          time-to-sleep (- update-period millis-since-last-run)]
      (if (not (pos? time-to-sleep))
        nil
        time-to-sleep))))

(spec/fdef get-sleep-time :args (spec/cat :last-run #(instance? DateTime %)
                                          :update-period pos-int?)
                          :ret (spec/nilable pos-int?))

(defn perform-new-inquiry! [base-data, runtime-data]
  (println "Performing new inquiry!")
  (let [retrieved-data (->> base-data
                            :url
                            (fetch-document)
                            (get-result-rows)
                            (parse-result-rows))
        pre-filtered-data (basic-filter/filter base-data retrieved-data)
        complete-filtered-data (relevance-filter/filter runtime-data pre-filtered-data)]
    (when (not (empty? complete-filtered-data))
      (email/email complete-filtered-data))
    (persistence/update-runtime-data! complete-filtered-data)))

(defn main-loop []
  (println "Starting main loop.")
  (let [base-data (utils/get-base-data)]
    (loop []
      (let [runtime-data (utils/get-runtime-data)
            last-run (utils/get-last-run runtime-data)
            sleep-time (get-sleep-time last-run (:update-period base-data))]
        (if sleep-time
          (do
            (println (format "Sleeping %s mms." sleep-time))
            (Thread/sleep sleep-time))
          ;else
          (do
            ;(Thread/sleep 5000) ;; Try to avoid getting blocked in any case.
            (perform-new-inquiry! base-data, runtime-data)))
        (recur)))))

(defn -main
  [& args]
  (main-loop))


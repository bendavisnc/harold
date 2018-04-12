(ns harold.core
  "A personal craigslist scraper to find me a place to live."
  (:gen-class)
  (:require [clojure.java.io :as io]
            [harold.parsing.core :as parsing]
            [harold.model.item-info :as item-info]
            [harold.filtering.basic :as basic-filter]
            [harold.filtering.relevant :as relevance-filter]
            [clj-time.core :as t]
            [orchestra.spec.test :as orchestra]
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


(defn get-result-rows 
  "Given a JSoup doc, return the elements that we're interested in."
  [doc]
  (.select doc "li.result-row"))

(defn parse-result-rows [rows]
  "Given a list of the elements that we're interested in, return a list of ItemInfo records."
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

(defn perform-new-inquiry! 
  "This is a multistep `process`:
     1. Retrieve html doc from craigslist
     2. Parse doc into data records
     3. Filter records based on rules and previously seen
     4. Email out only the new unseen record items."
  [base-data, runtime-data]
  (println "Performing new inquiry!")
  (let [retrieved-data (->> base-data
                            :url
                            (fetch-document)
                            (get-result-rows)
                            (parse-result-rows))
        pre-filtered-data (basic-filter/filter base-data retrieved-data)
        complete-filtered-data (relevance-filter/filter runtime-data pre-filtered-data)]
    (when (not (empty? complete-filtered-data))
      (email/email! complete-filtered-data))
    (persistence/update-runtime-data! complete-filtered-data)))

(defn main-loop 
  "This is an infinite loop that simply runs our inquiry function based on a configured interval time amount."
  []
  (println "Starting main loop.")
  (let [base-data (utils/get-base-data)]
    (loop []
      (let [runtime-data (utils/get-runtime-data)
            last-run (utils/get-last-run runtime-data)
            sleep-time (get-sleep-time last-run (:update-period base-data))]
        (if sleep-time
          (do
            (println (format "Sleeping %s ms." sleep-time))
            (Thread/sleep sleep-time))
          ;else
          (do
            ;(Thread/sleep 5000) ;; Try to avoid getting blocked in any case.
            (perform-new-inquiry! base-data, runtime-data)))
        (recur)))))

(defn -main
  "Starts the main infinite loop."
  [& args]
  (main-loop))


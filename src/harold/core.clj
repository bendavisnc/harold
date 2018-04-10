(ns harold.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [harold.parsing.core :as parsing]
            [harold.model.item-info :as item-info])
  (:import [org.jsoup Jsoup]))

(def data
  {:url "https://raleigh.craigslist.org/d/sublets-temporary/search/sub"})

(defn fetch-document [url]
  (-> url
      (Jsoup/connect)
      (.get)))

(defn get-result-rows [doc]
  (.select doc "li.result-row"))


(defn main* []
  (->> data
       :url
       (fetch-document)
       (get-result-rows)
       (map (fn [e]
              (item-info/create :description (parsing/description e)
                                :time (parsing/time e)
                                :price (parsing/price e))))))

(defn -main
  [& args]
  (time (println (main*))))


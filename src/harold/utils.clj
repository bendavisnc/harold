(ns harold.utils
  (:require [clj-time.format :as t-format]
            [harold.constants :as constants]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn get-last-run [runtime-data]
  (when-let [last-run* (:last-run runtime-data)]
    (t-format/parse (t-format/formatters constants/time-format) last-run*)))

(defn get-base-data []
  (edn/read-string (slurp (io/resource "base-data.edn"))))

(defn get-runtime-data []
  (edn/read-string (slurp (io/resource "runtime-data.edn"))))

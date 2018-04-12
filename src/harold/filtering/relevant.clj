(ns harold.filtering.relevant
  "Filters items based on if they're `relevant`, ie, should be broadcasted."
  (:refer-clojure :exclude [filter])
  (:require [harold.utils :as utils]
            [clj-time.core :as t]
            [harold.model.item-info :as item-info]))

;(defn- old? [runtime-data, item]
;  (if-let [last-run (utils/get-last-run runtime-data)]
;    (t/before? (:time item) last-run)
;    ;else
;    false))

(defn- seen? [runtime-data, item]
  (boolean (some #{(hash (item-info/with-pretty-time item))}
                 (:seen-hashes runtime-data))))


(defn filter [runtime-data, items]
  (clojure.core/filter (fn [item]
                         ;(and (not (old? runtime-data item))
                          (not (seen? runtime-data item)))
                       items))



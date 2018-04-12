(ns harold.filtering.relevant
  "Filters items based on if they're `relevant`, ie, are new and should be emailed out."
  (:refer-clojure :exclude [filter])
  (:require [harold.model.item-info :as item-info]))

(defn- seen? [runtime-data, item]
  (boolean (some #{(hash (item-info/with-pretty-time item))}
                 (:seen-hashes runtime-data))))


(defn filter [runtime-data, items]
  (clojure.core/filter (fn [item]
                          (not (seen? runtime-data item)))
                       items))



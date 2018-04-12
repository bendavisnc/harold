(ns harold.model.item-info
  "Provides a simple record construct that defines all of the item data we're interested in."
  (:require [clojure.spec.alpha :as spec]
            [clj-time.format :as t-format]
            [harold.constants :as constants])
  (:import (org.joda.time DateTime)))

(defrecord ItemInfo [description, time, price])

(defn create [& {:keys [description, time, price, url] :as m}]
  (map->ItemInfo m))

(spec/def ::description string?)
(spec/def ::time #(instance? DateTime %))
(spec/def ::price (spec/nilable pos-int?))
(spec/def ::url string?)
(spec/fdef create :args (spec/cat :kwargs (spec/keys* :req-un [::description, ::time, ::price, ::url]))
                  :ret #(instance? ItemInfo %))


(defn with-pretty-time 
  "Takes a supplied ItemInfo record and returns the :time value as a string."
  [info]
  (update info :time #(t-format/unparse (t-format/formatters constants/time-format) %)))

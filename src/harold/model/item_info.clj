(ns harold.model.item-info
  (:require [clojure.spec.alpha :as spec]
            [clj-time.core :as t]
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

;(def ^:private pretty-time-format (t-format/formatter "E, MMM d (KK:mm a)"))

(defn with-pretty-time [info]
  ;(update info :time #(t-format/unparse pretty-time-format %))
  (update info :time #(t-format/unparse (t-format/formatters constants/time-format) %)))

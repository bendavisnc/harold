(ns harold.services.persistence
  (:require [clojure.tools.logging :as log]
            [harold.utils :as utils]
            [clj-time.core :as t]
            [clj-time.format :as t-format]
            [harold.constants :as constants]
            [harold.model.item-info :as item-info]))

(defn- update-last-run [runtime-data]
  (assoc runtime-data :last-run (t-format/unparse (t-format/formatters constants/time-format) (t/now))))

(defn- update-seen-hashes [runtime-data, items]
  (reduce (fn [acc, item]
            (update acc :seen-hashes conj (hash (item-info/with-pretty-time item))))
          runtime-data
          items))


(defn update-runtime-data! [items]
  (log/info "Persisting runtime data.")
  (let [existing-data (utils/get-runtime-data)]
    (spit "./resources/runtime-data.edn"
          (-> existing-data
              (update-last-run)
              (update-seen-hashes items)))))



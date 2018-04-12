(ns harold.persistence.core
  (:require                                                 ;[clojure.tools.logging :as log]
    [harold.utils :as utils]
    [clj-time.core :as t]
    [clj-time.format :as t-format]
    [harold.constants :as constants]
    [harold.model.item-info :as item-info]
    [clojure.pprint :as pprint]
    [clojure.java.io :as io]))

(defn- update-last-run [runtime-data]
  (assoc runtime-data :last-run (t-format/unparse (t-format/formatters constants/time-format) (t/now))))

(defn- update-seen-hashes [runtime-data, items]
  (reduce (fn [acc, item]
            (update acc :seen-hashes conj (hash (item-info/with-pretty-time item))))
          runtime-data
          items))

(defn update-runtime-data! [items]
  (let [existing-data (utils/get-runtime-data)]
    (println "Persisting runtime data.")
    (with-open [w (io/writer (io/resource "runtime-data.edn"))]
      (.write w (with-out-str
                  (pprint/pprint
                    (-> existing-data
                        (update-last-run)
                        (update-seen-hashes items))))))))


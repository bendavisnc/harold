(ns harold.filtering.basic
  (:refer-clojure :exclude [filter]))

(defn- right-price? [config-data, item]
  (<= (or (:price item), 0) (:max-price config-data)))

(defn- blacklisted? [config-data, item]
  (let [description (:description item)]
    (loop [[filter-word & rest-words :as filter-words] (:filter-words config-data)]
      (cond
        (empty? filter-words) false
        (.contains description filter-word) true
        :else (recur rest-words)))))



(defn filter [config-data, app-data]
   (clojure.core/filter (fn [item]
                          (or (not (right-price? config-data item))
                              (blacklisted? config-data item)))
                        app-data))




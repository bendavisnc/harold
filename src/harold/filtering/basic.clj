(ns harold.filtering.basic
  "Filters items based on basic, standard rules."
  (:refer-clojure :exclude [filter]))

(defn- right-price?
  "Returns true if the supplied item's price is less than or equal to the configured max price."
  [base-data, item]
  (<= (or (:price item), 0) (:max-price base-data)))

(defn- blacklisted?
  "Returns true if the item's description contains a word from the configured filter words."
  [base-data, item]
  (let [description (:description item)]
    (loop [[filter-word & rest-words :as filter-words] (:filter-words base-data)]
      (cond
        (empty? filter-words) false
        (.contains description filter-word) true
        :else (recur rest-words)))))



(defn filter [base-data, items]
   (clojure.core/filter (fn [item]
                          (and (right-price? base-data item)
                               (not (blacklisted? base-data item))))
                        items))




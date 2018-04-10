(ns harold.parsing.core
  (:refer-clojure :exclude [time]))

;(def price-regex #"\$(.*)\D")
(def price-regex #"\$(\d*)")

;(def test-price )

(defn description [e]
  (.html (.select e "a.result-title")))

(defn price [e]
  (when-let [price-text (last (re-find price-regex
                                       (.html (.select e "span.result-price"))))]
    (Integer/parseInt price-text)))
;(defn price [e]
;  (try
;    (Integer/parseInt (last (re-find price-regex
;                                     (.html (.select e "span.result-price")))))
;    (catch Exception ex
;      (throw (new Exception (format "Problem parsing price (%s)." (.html (.select e "span.result-price")))
;                            ex)))))

(defn time [e]
  (.get (.attributes (first (.select e "time.result-date")))
        "datetime"))

;(defn funky []
;  (re-find price-regex "$395\n$395"))

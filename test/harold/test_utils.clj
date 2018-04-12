(ns harold.test-utils
  (:require [clojure.string :as string]))


(defn remove-last-lines [s, n]
  (-> s
      (string/split-lines)
      (as-> lines (subvec lines
                          0,
                          (- (count lines) n)))
      (as-> lines (string/join lines "/n"))))

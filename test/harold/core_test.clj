(ns harold.core-test
  (:require [clojure.test :refer :all]
            [harold.core :as harold]
            [harold.model.item-info :as item-info]
            [harold.filtering.basic :as basic-filter]
            [harold.utils :as utils]
            [clj-time.core :as t]
            [clojure.java.io :as io]
            [harold.test-utils :refer [remove-last-lines]])
  (:import (java.io StringWriter)))

(defn setup-and-teardown [run-tests!]
  (let [original-runtime-data (slurp (io/resource "runtime-data.edn"))]
    (binding [harold.services.email/disabled? true]
      (run-tests!))
    (spit (io/resource "runtime-data.edn") original-runtime-data)))

(use-fixtures :once setup-and-teardown)


(deftest harold-core-tests

  (testing "harold/get-result-rows"
    (is (= 120
           (count (harold/get-result-rows
                    (harold/fetch-document (:url (utils/get-base-data))))))))

  (testing "harold/get-result-rows"
    (is (= (read-string (slurp (io/resource "result-rows.edn")))
           (map item-info/with-pretty-time
                (harold/parse-result-rows
                  (harold/get-result-rows
                           (harold/fetch-document (:url (utils/get-base-data)))))))))

  (testing "harold.filtering.basic"
    (is (= 49
           (count (basic-filter/filter (utils/get-base-data)
                                       (harold/parse-result-rows
                                         (harold/get-result-rows
                                           (harold/fetch-document (:url (utils/get-base-data))))))))))

  (testing "harold whole shebang"
    (let [wait-time 1000
          start-time (t/now)
          out-capture (new StringWriter)
          async-process (future (binding [clojure.core/*out* out-capture]
                                  (harold/main-loop)))]
      (while (< (- (.getMillis (t/now))
                   (.getMillis start-time))
                wait-time)
        (Thread/sleep 1000))
      (future-cancel async-process)
      ;(spit "./test/resources/shebang-capture.txt" out-capture)
      (is (= (remove-last-lines (slurp (io/resource "whole-shebang-result.txt")), 1)
             (remove-last-lines (str out-capture), 1))))))

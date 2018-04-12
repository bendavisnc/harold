(ns harold.services.email
  (:require [clostache.parser :as m]
            [postal.core :as e]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(def ^:private lazy-email-creds (atom nil))
(defn- get-email-creds []
  (or (deref lazy-email-creds)
      (let [retrieved-creds (edn/read-string (slurp (io/resource "email-creds.edn")))]
        (reset! lazy-email-creds retrieved-creds)
        retrieved-creds)))

(defn- get-username [email-address]
  (subs email-address 0 (.indexOf email-address "@")))

(defn send-email [subject, body]
  (let [{:keys [address, password]} (get-email-creds)]
    (e/send-message {:host "smtp.gmail.com"
                     :user (get-username address)
                     :pass password
                     :ssl true},
                    {:from address
                     :to address
                     :subject subject
                     :body body})))


(defn email [items]
  (println "Sending new email...")
  (println (m/render-resource "email-template.txt" {:count (count items)
                                                    :items (map #(assoc %1 :index (inc %2))
                                                                items
                                                                (range (count items)))})))




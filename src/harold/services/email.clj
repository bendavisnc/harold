(ns harold.services.email
  "Provides a function for actually sending an email based on new items we've found of interest."
  (:require [clostache.parser :as m]
            [postal.core :as e]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))


(def ^:dynamic disabled? false) ; Flag to disable actually sending an email (for testing).

(def ^:private lazy-email-creds (atom nil))
(defn- get-email-creds []
  (or (deref lazy-email-creds)
      (let [retrieved-creds (edn/read-string (slurp (io/resource "email-creds.edn")))]
        (reset! lazy-email-creds retrieved-creds)
        retrieved-creds)))

(defn- get-username [email-address]
  (subs email-address 0 (.indexOf email-address "@")))

(defn- add-indexes [items]
  (map #(assoc %1 :index (inc %2))
       items
       (range (count items))))



(defn- send-email! [subject, body]
  (let [{:keys [address, password]} (get-email-creds)]
    (e/send-message {:host "smtp.gmail.com"
                     :user (get-username address)
                     :pass password
                     :ssl true},
                    {:from address
                     :to address
                     :subject subject
                     :body body})))


(defn email! [items]
  (let [render-data {:count (count items)
                     :items (add-indexes items)}
        subject-line (m/render-resource "email-subject-template.txt" render-data)
        body (m/render-resource "email-body-template.txt" render-data)]
    (println "Sending new email...")
    (println "subject...")
    (println subject-line)
    (println "body...")
    (println body)
    (when (not disabled?)
      (send-email! subject-line, body))))


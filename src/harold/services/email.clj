(ns harold.services.email
  (:require [clostache.parser :as m]))
  ;(:require [clojure.tools.logging :as log]))

(defn email [items]
  (println "Sending new email...")
  (println (m/render-resource "email-template.txt" {:count (count items)
                                                    :items (map #(assoc %1 :index (inc %2))
                                                                items
                                                                (range (count items)))})))




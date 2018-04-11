(ns harold.services.email
  (:require [clojure.tools.logging :as log]))

(defn email [items]
  (log/info "Sending new email...")
  (log/info items))

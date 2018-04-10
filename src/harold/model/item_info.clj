(ns harold.model.item-info)

(defrecord ItemInfo [description, time, price])

(defn create [& {:keys [description, time, price] :as m}]
  (map->ItemInfo m))
(defproject harold "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"], [org.jsoup/jsoup "1.11.2"]]
  :main ^:skip-aot harold.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
  ;:repl-options {:init-ns harold.parsing.core})


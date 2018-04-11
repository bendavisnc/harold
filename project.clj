(defproject harold "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"],
                 [org.jsoup/jsoup "1.11.2"],
                 [clj-time "0.14.3"],
                 [orchestra "2017.11.12-1"]
                 [org.clojure/tools.logging "0.4.0"]]
  :main ^:skip-aot harold.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

  ;:repl-options {:init-ns harold.parsing.core})


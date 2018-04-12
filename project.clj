(defproject harold "0.1.0-SNAPSHOT"
  :description "A personal craigslist scraper to find me a place to live."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"],
                 [org.jsoup/jsoup "1.11.2"],
                 [clj-time "0.14.3"],
                 [orchestra "2017.11.12-1"]
                 [org.clojure/tools.logging "0.4.0"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 ;[mmemail "1.0.1"]
                 [com.draines/postal "2.0.2"]]
  :main ^:skip-aot harold.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:resource-paths ["test/resources"]}
             :prod {:resource-paths ["resources"]}}

  ;:repl-options {:init-ns harold.parsing.core}
  :repl-options {:init-ns harold.core-test}
  :aliases {"launch" ["with-profile", "prod", "run"]})


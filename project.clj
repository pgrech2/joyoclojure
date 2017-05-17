(defproject joyoclojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [clj-http "2.3.0"]
                 [clj-time "0.13.0"]
                 [cheshire "5.6.3"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.stuartsierra/component "0.3.2"]]
                   :jvm-opts     ["-Xmx6g"]
                   :source-paths ["dev"]}})

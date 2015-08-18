(defproject net.dossot/clatch "1.0.0-SNAPSHOT"
  :description "A Clojure-powered Scratch."
  :url "https://www.github.com/ddossot/clatch"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"
            :comments "Copyright (c) 2015 David Dossot"}

  :plugins [[lein-modules "0.3.11"]]

  :modules {:versions {:gdx-version "1.6.4"}}

  :profiles {:provided {:dependencies [[org.clojure/clojure "1.7.0"]]}
             :dev {:plugins [[lein-kibit "0.1.2"]
                             [jonase/eastwood "0.2.1"]]}})

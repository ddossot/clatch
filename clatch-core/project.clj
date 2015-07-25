(defproject net.dossot.clatch/clatch-core "1.0.0-SNAPSHOT"
  :description "Clatch core libraries."
  :url "https://www.github.com/ddossot/clatch"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"
            :comments "Copyright (c) 2015 David Dossot"}

  :plugins [[lein-modules "0.3.11"]]

  :dependencies
  [
   [prismatic/schema "0.4.3"]
   [play-clj "0.4.7"]
  ])

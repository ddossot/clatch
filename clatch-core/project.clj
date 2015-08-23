(defproject net.dossot.clatch/clatch-core "1.0.0-SNAPSHOT"
  :description "Clatch core libraries."
  :url "https://www.github.com/ddossot/clatch"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"
            :comments "Copyright (c) 2015 David Dossot"}

  :plugins [[lein-modules "0.3.11"]]

  :profiles {:dev {:dependencies [[com.badlogicgames.gdx/gdx-backend-lwjgl :gdx-version]]
                   :eastwood {:config-files ["eastwood-config.clj"]}}}

  :dependencies
  [
   [org.clojure/clojure :clj-version]
   
   [play-clj "0.4.7" :exclusions [com.badlogicgames.gdx/gdx]]
   [prismatic/schema "0.4.3"]
   [manifold "0.1.1-alpha3"]
  ])

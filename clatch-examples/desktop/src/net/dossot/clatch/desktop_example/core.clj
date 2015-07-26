(ns net.dossot.clatch.desktop-example.core
  (:require [net.dossot.clatch.core :refer [defproject]]
            [net.dossot.clatch.desktop :refer [start]])
  (:gen-class))

(defproject example-project
  "Clatch example project"
  (stage
    (backdrops
      :moon  "backdrops/moon.png"
      :stars "backdrops/stars.png")
    (scripts
      (when-start
        (switch-backdrop-to :moon)
        (forever
          (next-backdrop)
          (wait-seconds 1.5))))))

(defn -main []
  (start example-project))

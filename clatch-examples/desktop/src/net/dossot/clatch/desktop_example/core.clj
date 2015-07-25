(ns net.dossot.clatch.desktop-example.core
  (:require [net.dossot.clatch.core :refer [defproject]]
            [net.dossot.clatch.desktop :refer [start]])
  (:gen-class))

(defproject example-project
  "Clatch example project"
  (backdrops
    :backdrop1 "backdrops/moon.png"))

(defn -main []
  (start example-project))

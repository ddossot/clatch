(ns clatch.desktop-example.core
  (:require [clatch.core :refer [defproject]]
            [clatch.events :refer [when-start]]
            [clatch.desktop :refer [start]])
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

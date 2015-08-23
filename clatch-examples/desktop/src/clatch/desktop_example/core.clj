(ns clatch.desktop-example.core
  (:require [clatch :refer :all])
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
          (wait-seconds 1.5)
          (next-backdrop))))))

(defn -main []
  (start-on-desktop example-project))

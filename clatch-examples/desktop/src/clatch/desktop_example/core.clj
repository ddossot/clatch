(ns clatch.desktop-example.core
  (:require [clatch.core :refer [defproject]]
            [clatch.events :refer :all]
            [clatch.looks :refer :all]
            [clatch.control :refer :all]
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
          (wait-seconds 1.5)
          (next-backdrop))))))

(defn -main []
  (start example-project))

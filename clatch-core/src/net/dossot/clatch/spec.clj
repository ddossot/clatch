(ns net.dossot.clatch.spec
  (:require [schema.core :as s]))

(def Backdrops
  [(s/one (s/eq 'backdrops) "backdrops")
   (s/either s/Keyword s/Str)])

(def Script
  [(s/one s/Symbol "symbol")
   s/Any])

(def Scripts
  [(s/one (s/eq 'scripts) "scripts")
   Script])

(def Stage
  [(s/one (s/eq 'stage) "stage")
   (s/optional Backdrops "stage backdrops")
   (s/optional Scripts "stage scripts")])

(def Project
  [(s/maybe Stage)])

(defn validate-project
  [project]
  (s/validate Project project))

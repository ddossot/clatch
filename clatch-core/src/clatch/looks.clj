(ns clatch.looks
  (:require [clatch.utils :refer [publish-to-stage!]]))

(defmacro switch-backdrop-to
  ;; TODO doc
  [backdrop-id]
  {:pre [(keyword? backdrop-id)]}
  `(publish-to-stage!
     ~'event-bus
     [:switch-backdrop-to ~backdrop-id]))

(defmacro next-backdrop
  ;; TODO doc
  []
  `(publish-to-stage!
     ~'event-bus
     [:next-backdrop]))

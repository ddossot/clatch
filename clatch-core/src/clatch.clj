(ns clatch
  (:require [potemkin.namespaces :refer [import-vars]]
            [clatch core desktop control events looks]))

(import-vars
  [clatch.core

   defproject]

  [clatch.desktop

   start-on-desktop]

  [clatch.control

   forever
   wait-seconds]

  [clatch.events

   when-start]

  [clatch.looks

   switch-backdrop-to
   next-backdrop])

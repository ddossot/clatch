(ns clatch.events)

(defmacro when-start
  "Called when the project starts.
   Equivalent to Scratch's green flag clicked event."
  [& forms]
  `(future ~@forms))

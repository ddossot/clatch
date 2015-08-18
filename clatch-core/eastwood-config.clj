(disable-warning
 {:linter :suspicious-expression
  :for-macro 'clojure.core/doto
  :if-inside-macroexpansion-of #{'play-clj.core/stage 'play-clj.g2d/texture}
  :within-depth 6
  :reason "some play-clj macros only expand to (doto x) expressions"})


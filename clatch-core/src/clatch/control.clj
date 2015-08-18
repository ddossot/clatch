(ns clatch.control)

(defmacro forever
  ;; TODO doc
  [& forms]
  `(loop []
     ~@forms
     (recur)))

(defn wait-seconds
  ;; TODO doc
  [duration]
  {:pre [(pos? duration)]}
  (Thread/sleep
    (* duration 1000)))

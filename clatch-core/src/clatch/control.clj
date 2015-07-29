(ns clatch.control)

(defmacro forever
  ;; TODO doc
  [& forms]
  (while true
    ~@forms))

(defn wait-seconds
  ;; TODO doc
  [duration]
  {:pre [(pos? duration)]}
  (Thread/sleep
    (* duration 1000)))

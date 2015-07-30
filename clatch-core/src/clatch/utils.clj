(ns clatch.utils)

(defn get-form
  [forms sym]
  (rest
    (first
      (filter
        #(= (first %) sym)
        forms))))

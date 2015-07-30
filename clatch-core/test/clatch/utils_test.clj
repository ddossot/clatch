(ns clatch.utils-test
  (:require [clojure.test :refer :all]
            [clatch.utils :as cu]))

(deftest next-in-cyclic-list
  (let [sut (range 1 10)]
    (are [a e] (= a e)
      (cu/next-in-cyclic-list sut 1) 2
      (cu/next-in-cyclic-list sut 5) 6
      (cu/next-in-cyclic-list sut 9) 10
      (cu/next-in-cyclic-list sut 10) 1
      (cu/next-in-cyclic-list sut 0) nil
      (cu/next-in-cyclic-list sut 11) nil
      (cu/next-in-cyclic-list sut :bad) nil)))

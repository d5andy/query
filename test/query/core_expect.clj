(ns query.core-expect
  (:use expectations))

(println "expectations")
(expect 3 (+ 1 1))

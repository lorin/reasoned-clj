;; # 1. Playthings
(ns ch1
    {:clj-kondo/config 
   		'{:linters {:unresolved-symbol {:level :off}
                 :type-mismatch {:level :off}}}})

(require '[clojure.core.logic :refer [s# u# run*]])

;; succeed is `s#` instead of `#s`
s#

;; fail is `u#` instead of `#u`
u#

;; When a goal fails, the result is `()`
(run* [q] u#)

;; The result here should be `(true)`
(run* [q]
  (== true q))


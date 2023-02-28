;; # Ch 1. Playthings

;; [clj-kondo] doesn't understand core.logic so we need
;; to turn off some of the linting.
;;
;; [clj-kondo]: https://cljdoc.org/d/clj-kondo
(ns ch1
  {:clj-kondo/config
   '{:linters {:unresolved-symbol {:level :off}
               :type-mismatch {:level :off}}}} 
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer [s# u# == run run* fresh conde]]))

;; succeed is `s#` instead of `#s`. 
s#

;; fail is `u#` instead of `#u`
u#

;; When a goal fails, the result is `()`
(run* [q] u#)
 

;; The result here should be `(true)`
(run* [q]
  (== true q))

;; `()` because it fails
(run* [q]
  u#
  (== true q))

;; A successful goal
(run* [q]
  s#
  (== true q))

; this should be `(corn)`
(run* [r] 
      s# 
      (== 'corn r))

; should be `()` because it fails

(run* [r] 
      u# 
      (== 'corn r))

; => `(false)`
(run* [q]
      s# 
      (== false q))


; this fails
(run* [r]
     (let [x false]
       (== true x)))


; this should be: `(true)`
(run* [q]
  (fresh [x]
     (== true x)
     (== true q)))

;; ## The Law of Fresh
;; If *x* is fresh, then `(== v x)` succeeds and associates *x* with *v*

; this should be `(true)`
(run* [q]
      (fresh [x]
             (== x true)
             (== true q)))

; same as above, we've just changed ordering
(run* [q]
      (fresh [x]
             (== x true)
             (== q true)))

;; ## The Law of `==`
;; `(== v w)` is the same as `(== w v)`

; This can be anything. The result is a symbol representing a fresh variable.
(run* [x] 
      s#)

; Because of scoping, the result should be `(_0)`
(run* [x]
      (let [x false]
        (fresh [x]
               (== true x))))

; This should be: `(_0 _1)`
(run* [r]
  (fresh [x y]
    (== (cons x (cons y ())) r)))

; this should be same as above
(run* [s]
  (fresh [t u]
    (== (cons t (cons u ())) s))) 

;  →  `(_0 _1 _0)`
(run* [r]
  (fresh [x]
    (let [y x]
      (fresh [x]
          (== (cons y (cons x (cons y ()))) r)))))

; →  `(_1 _0 _1)`
; (turned out to be the other way around, but semantically teh same)
(run* [r]
  (fresh [x]
    (let [y x]
      (fresh [x]
          (== (cons x (cons y (cons x ()))) r)))))


; → `()`
; Can't satisfy the goal
(run* [q]
      (== false q)
      (== true q))

; → `(false)`
(run* [q]
      (== false q)
      (== false q)) 

; → `(true)`
(run* [q]
      (let [x q]
        (== true x)))

; → `(_0)`
(run* [r]
      (fresh [x] 
             (== x r)))

; → `(true)`
(run* [q]
      (fresh [x]
             (== x q)
             (== true x)))


;; `conde` does an *or* of each vector argument.
;; Within each vector, it does an *and*.

(conde
  [u# s#]
  [s# u#])

(conde
 [u# u#]
 [s# s#])


; `(olive oil)`
(run* [x]
      (conde
        [(== 'olive x)]
        [(== 'oil x)]))

; ## The Law of conde
; To get more values from conde, pretend that the successful conde line has
; failed, refreshing all variables that got an association from that line.

(run 1 [x] 
     (conde 
      [(== 'olive x)]
      [(== 'oil x)]))

(run* [r]
      (fresh [x y]
             (conde 
               [(== 'split x) (== 'pea y)]
               [(== 'navy x) (== 'bean y)])
             (== (cons x (cons y ())) r)))
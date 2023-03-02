;; # Ch 2. Teaching old toys new tricks

;; [clj-kondo] doesn't understand core.logic so we need
;; to turn off some of the linting.
;;
;; [clj-kondo]: https://cljdoc.org/d/clj-kondo
(ns ch2
  {:clj-kondo/config
   '{:linters {:unresolved-symbol {:level :off}
               :type-mismatch {:level :off}}}} 
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer [== run* fresh firsto resto lcons]]))


; In clojure, it's firsto and not caro
(run* [r]
      (firsto '(grape raisin pear) r))

; Feels more clojure-y to use prefer vectors of keywords instead of lists of symbols
(run* [r]
      (firsto [:grape :raisin :pear] r))

; You have to use `lcons` instead of `cons.`
; See https://github.com/clojure/core.logic/wiki/Differences-from-The-Reasoned-Schemer
(run* [r]
  (fresh [x y]
      (firsto '(grape raisin pear) x)
      (firsto '((a) (b) (c)) y)
      (== (lcons x y) r)))


; A little more clojure-y
(run* [r]
  (fresh [x y]
      (firsto [:grape :raisin :pear] x)
      (firsto [[:a] [:b] [:c]] y)
      (== (lcons x y) r)))

(run* [r]
  (resto '(a c o r n) r))

(run* [r]
  (resto [:a :c :o :r :n] r))

(run* [r]
    (fresh [v]
        (resto [:a :c :o :r :n] v)
        (firsto v r)))
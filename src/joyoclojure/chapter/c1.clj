(ns joyoclojure.chapter.c1)


(defn hello-world
  [x]
  (inc x))

;; Polymorphism
;; * Ability of a function/method to perform different actions depending on
;;   type of argument or target object.
;; * Provided in clojure via protocols

(defprotocol Concatenatable
  (cat [this other]))

(extend-type String
  Concatenatable
  (cat [this other]
    (.concat this other)))

(extend-type java.util.List
  Concatenatable
  (cat [this other]
    (concat this other)))
;; WHY .CONCAT VS CONCAT

;; Expression problem

;; Subtyping & Interface-Oriented Programming
;; * Protocols allow for defining logically grouped set of functions
;; * Abstraction-oriented programming

;; Encapsulation

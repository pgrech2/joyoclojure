(ns joyoclojure.chpt10-metaphysics)


;; Three types of state references:
;;   atom
;;   ref
;;   var


;; Example of an atom
(def fred (atom {:cuddle-hunger-level 0
                 :percent-deteriorated 0}))

(defn fred-state
  []
  @fred)

(defn ex1
  []
  (let [{:keys [cuddle-hunger-level
                percent-deteriorated] :as state} @fred]
    (if (>= percent-deteriorated 5)
      (future (println cuddle-hunger-level)))))

(defn ex2
  []
  (swap! fred
         (fn [current-state]
           (merge-with + current-state {:cuddle-hunger-level  1
                                        :percent-deteriorated 1}))))

(defn ex2-level
  [state inc-by]
  (merge-with + state {:cuddle-hunger-level inc-by}))

;; Use case with ex2-level
;; ;; (swap! fred ex2-level 3)

;; ;; Same as above but with clojure built in core functions
;; (swap! fred update-in [:percent-deteriorated] + 5)

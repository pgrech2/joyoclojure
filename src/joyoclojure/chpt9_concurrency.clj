(ns joyoclojure.chpt9-concurrency)

(defn ex1
  []
  (future (Thread/sleep 400)
          (println "Waiting 4 seconds"))
  (println "Print NOW!"))

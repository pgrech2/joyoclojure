(ns joyoclojure.chpt9-concurrency)

(defn ex1
  "Wait on a thread for 4 seconds"
  []
  (future (Thread/sleep 4000)
          (println "Waiting 4 seconds"))
  (println "Print NOW!"))


(defn ex2
  "Demonstrates that a futures body will only run once."
  []
  (let [result (future (println "print once")
                       (inc 1))]
    (println "-------------")
    (println (str "deref: " (deref result)))
    (println (str "@: " @result))))

(defn ex3
  "Demonstrates time limit associated with a future, will return defualt value
    * Sleep thread 10 seconds
    * Return 5
    * Time limit 3 seconds
    * Otherwise return 0"
  []
  (-> (Thread/sleep 10000)
      (future 5)
      (deref 3000 0)))

(defn ex4
  "Realized - false - still running
   Realized - true - done running"
  []
  (let [f (future)]
    (when-not (realized? f) (println "Still running"))
    @f
    (when (realized? f) (println "Done Running"))))

(def delay-mssg
  (delay
   (let [message "Just call my name and I'll be there"]
     (println "First deref:" message)
     message)))


(defn email-user
  [email-address]
  (println "Sending headshots notification to: " email-address))

(defn upload-document
  "Needs to be implemented"
  [headshot]
  true)

(defn run-app
  []
  (let [headshots ["serious.jpg" "fun.jpg" "playful.jpg"]
        notify    (delay (email-user "and-my-axe@gmail.com"))]
    (doseq [hs headshots]
      (future (upload-document hs)
              (force notify)))))

(defn ex5
  []
  (let [p (promise)
        _ (deliver p (inc 1))]
    @p))

(def yak-butter-international
  {:store "Yak Butter International"
   :price 90
   :smoothness 90})

(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})

;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(defn best-butter-single
  []
  (time (some (comp satisfactory? mock-api-call)
              [yak-butter-international butter-than-nothing baby-got-yak])))

(defn best-butter-multi
  []
  (time
   (let [butter-promise (promise)]
     (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
       (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
                 (deliver butter-promise satisfactory-butter))))
     (deref butter-promise 5000 "Timed Out"))))

(defn ex6
  []
  (let [ferengi-wisdom-promise (promise)]
    (future (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))
    (Thread/sleep 100)
    (deliver ferengi-wisdom-promise "Whisper your way to success.")))

(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

(defn ex7
  []
  (let [saying3 (promise)]
    (future (deliver saying3 (wait 1000 "Cheerio!")))
    @(let [saying2 (promise)]
       (future (deliver saying2 (wait 6000 "Pip pip!")))
       @(let [saying1 (promise)]
          (future (deliver saying1 (wait 2000 "'Ello, gov'na!")))
          (println @saying1)
          saying1)
       (println @saying2)
       saying2)
    (println @saying3)
    saying3))

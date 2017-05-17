(ns joyoclojure.chess)

(def ^:dynamic *file-key* \a)
(def ^:dynamic *rank-key* \0)

(defn initial-board []
  [\r \n \b \q \k \b \n \r
   \p \p \p \p \p \p \p \p
   \- \- \- \- \- \- \- \-
   \- \- \- \- \- \- \- \-
   \- \- \- \- \- \- \- \-
   \- \- \- \- \- \- \- \-
   \P \P \P \P \P \P \P \P
   \R \N \B \Q \K \B \N \R])

(defn- file-component
  [file]
  (- (int file) (int *file-key*)))

(defn- rank-component
  [rank]
  (->> (int *rank-key*)
       (- (int rank))
       (- 8)
       (* 8)))

(defn- index
  [file rank]
  (+ (file-component file)
     (rank-component rank)))

(defn lookup
  [board pos]
  (let [[file rank] pos]
    (board (index file rank))))

;; BLOCK LEVEL ENCAPSULATION
(letfn [(index [file rank]
          (let [f (- (int file) (int \a))
                r (->> (int \0)
                       (- (int rank))
                       (- 8)
                       (* 8))]
            (+ f r)))]
  (defn lookup2
    [board pos]
    (let [[file rank] pos]
      (board (index file rank)))))

;; LOCAL ENCAPSULATION
(defn lookup3
  [board pos]
  (let [[file rank] (map int  pos)
        [fc rc]     (map int [\a \0])
        f           (- file fc)
        r           (->> rc (- rank) (- 8) (* 8))]
    (board (+ f r))))

(defn print-board
  [board]
  (let [idx     (range 1 (inc (count board)))
        i-board (zipmap idx board)]
    (doseq [i idx]
      (if (= (mod i 8) 0)
        (println (i-board i))
        (print   (str (i-board i) "  "))))))
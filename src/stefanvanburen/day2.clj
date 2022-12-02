(ns stefanvanburen.day2
  (:require
   [clojure.string :refer [split]]
   [clojure.java.io :as io]))

(def input (slurp (io/resource "dec2.txt")))

(defn letter-to-play [letter]
  (get {"A" :rock
        "B" :paper
        "C" :scissors
        "X" :rock
        "Y" :paper
        "Z" :scissors} letter))

(defn score-for-play [play]
  (get {:rock 1
        :paper 2
        :scissors 3} play))

(defn get-outcome [opponent-play my-play]
  (cond
    (= opponent-play my-play) :draw
    (or
     (and (= opponent-play :rock) (= my-play :paper))
     (and (= opponent-play :paper) (= my-play :scissors))
     (and (= opponent-play :scissors) (= my-play :rock)))
    :win

    :else :loss))

(defn score-for-outcome [outcome]
  (get {:win 6
        :draw 3
        :loss 0} outcome))

(defn score [[opponent-letter my-letter]]
  (let [opponent-play (letter-to-play opponent-letter)
        my-play (letter-to-play my-letter)
        outcome (get-outcome opponent-play my-play)]
    (+ (score-for-play my-play) (score-for-outcome outcome))))

(comment
  (score ["A" "Y"])
  (score ["B" "X"])
  (score ["C" "Z"]))

(apply +
       (map #(score %)
            (map #(split % #" ")
                 (split input #"\n")))) ; 9759

;;; In the first round, your opponent will choose Rock (A),
;;; and you need the round to end in a draw (Y),
;;; so you also choose Rock. This gives you a score of 1 + 3 = 4.

(defn want-outcome [letter]
  (get {"X" :loss
        "Y" :draw
        "Z" :win} letter))

(defn get-play-for-opponent-play [opponent-play wanted-outcome]
  ; (prn opponent-play wanted-outcome)
  (case wanted-outcome
    :draw opponent-play
    :loss (case opponent-play
            :rock :scissors
            :scissors :paper
            :paper :rock)
    :win (case opponent-play
           :scissors :rock
           :paper :scissors
           :rock :paper)))

(defn score-part-2 [[opponent-letter my-letter]]
  (let [opponent-play (letter-to-play opponent-letter)
        my-play (get-play-for-opponent-play opponent-play (want-outcome my-letter))
        outcome (get-outcome opponent-play my-play)]
    (+ (score-for-play my-play) (score-for-outcome outcome))))

(apply +
       (map #(score-part-2 %)
            (map #(split % #" ")
                 (split input #"\n")))) ; 12429

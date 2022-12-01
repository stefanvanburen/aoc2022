(ns stefanvanburen.day1
  (:require
   [clojure.string :refer [split]]
   [clojure.java.io :as io]))

(def input (slurp (io/resource "dec1.txt")))

(defn parse-input [input]
  (map #(split % #"\n")
       (split input #"\n\n")))

(defn total-elf [xs]
  (apply +
         (map #(Integer/parseInt %) xs)))

(defn total-elves [parsed-input]
  (map #(total-elf %) parsed-input))

(defn part1 []
  (->> (parse-input input)
       (total-elves)
       (apply max)))

(defn part2 []
  (->> (parse-input input)
       (total-elves)
       (sort >)
       (take 3)
       (apply +)))

(= (part1) 70116)
(= (part2) 206582)

(ns stefanvanburen.aoc2022
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
  (apply max
         (total-elves (parse-input input)))) ; 70116

(defn part2 []
  (apply +
         (take 3
               (sort > (total-elves (parse-input input)))))) ; 206582

(= (part1) 70116)
(= (part2) 206582)

(ns mc-tools.core
  (:use clojure.test))

(defn mc-map
  ([threads f col]
     (mapcat #(pmap f %) (partition-all threads col)))
  ([threads f c1 c2]
     (let [inter-col (apply interleave 
                           (map #(partition-all threads %) (list c1 c2)))
           map-fn (fn map-fn [x] (apply #(pmap f %1 %2) x))
           step (fn step [col] (lazy-seq
                           (if (= (count col) 0)
                             ()
                             (concat (map-fn (take 2 col)) (step (drop 2 col))))))]
       (step inter-col))))


(defn myfun [x]
  (do (Thread/sleep 200)
      (* 10 x)))

 (defn myfun2 [x y]
  (do (Thread/sleep 200)
      (* x y)))


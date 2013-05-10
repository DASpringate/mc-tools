(ns mc-tools.tests
  (:use clojure.test mc-tools.core))

(deftest test-mc-map
  (let [a (range 1 50)
        b (range 51 100)
        cores1 1
        cores2 10
        cores3 24
        testfn (fn [x]
                 (do (Thread/sleep 100)
                     (* 10 x)))
        map-a (map testfn a)
        map-ab (map + a b)]
    (is (= (testfn 10) 100))
    (is (= (count map-a) (count a)))
    (is (= (count map-ab) (count a) (count b)))
    (is (= (mc-map cores1 testfn a) map-a))
    (is (= (mc-map cores2 testfn a) map-a))
    (is (= (mc-map cores3 testfn a) map-a))
    (is (= (mc-map cores1 + a b) map-ab))
    (is (= (mc-map cores2 + a b) map-ab))
    (is (= (mc-map cores3 + a b) map-ab))))

(defn -main []
  (run-tests 'mc-tools.tests))

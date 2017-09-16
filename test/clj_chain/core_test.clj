(ns clj-chain.core-test
  (:require [clojure.test :refer :all]
            [clj-chain.chain :refer :all]))

(deftest test-block
  (testing "Create a new block."
    (let [b (block 1, "phash", 111, "data", "hash")]
      (is (= 1 (:index b)))
      (is (= "phash" (:previous b)))
      (is (= 111 (:timestamp b)))
      (is (= "data" (:data b)))
      (is (= "hash" (:hash b))))))


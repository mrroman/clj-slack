(ns clj-slack.files-test
  (:require [clojure.test :refer :all]
            [clj-slack.files :refer :all])
  (:refer-clojure :exclude [list]))

(def connection (clj-slack.core/create-connection "https://slack.com/api" {:app (System/getenv "TOKEN")}))

(deftest file-upload
  (testing "Uploading a file"
    (is (true? (:ok (clj-slack.files/upload connection
                                            (clojure.java.io/input-stream "test/support/clojure.png")
                                            {:channels "C03PHJ58Z"}))))))

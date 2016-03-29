(ns clj-slack.groups-test
  (:require [clojure.test :refer :all]
            [clj-slack.groups :refer :all]))

(def connection (clj-slack.core/connection "https://slack.com/api" {:app (System/getenv "TOKEN")}))

(deftest group-list
  (testing "Listing groups"
    (is (:ok (clj-slack.groups/list connection)))))

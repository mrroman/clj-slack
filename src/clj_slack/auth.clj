(ns clj-slack.auth
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [test]))

(defn test
  "Checks authentication & identity."
  [connection]
  (app-request connection "auth.test"))

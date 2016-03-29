(ns clj-slack.api
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [test]))

(defn test
  "Helps you test your calling code.
  Optional arguments are:
  - error: error response to return
  - foo: example property to return"
  ([connection]
   (test connection {}))
  ([connection optionals]
   (app-request connection "api.test" optionals)))

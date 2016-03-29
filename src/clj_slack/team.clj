(ns clj-slack.team
  (:require [clj-slack.core :refer [app-request]]))

(defn access-log
  "Gets the access logs for the current team.
  Optional arguments are:
  count: number of items to return per page
  page: page number of results to return"
  ([connection]
    (app-request connection "team.accessLogs"))
  ([connection optionals]
   (app-request connection "team.accessLogs" optionals)))

(defn info
  "Gets information about the current team."
  [connection]
  (app-request connection "team.info"))

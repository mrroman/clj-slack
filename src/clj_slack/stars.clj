(ns clj-slack.stars
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [list]))

(defn list
  "Lists stars for a user.
  Optional arguments are:
  - user: show stars by a user
  - count: number of items to return per page
  - page: page number of results to return"
  ([connection]
   (list connection {}))
  ([connection optionals]
   (app-request connection "stars.list" optionals)))

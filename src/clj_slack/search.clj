(ns clj-slack.search
  (:require [clj-slack.core :refer [app-request]]))

(defn all
  "Searches for messages and files matching a query.
  Optional arguments are:
  - sort: return matches sorted by either score or timestamp
  - sort_dir: change sort direction
  - highlight: enable query highlight markers
  - count: number of items to return per page
  - page: page number of results to return"
  ([connection query]
   (all connection query {}))
  ([connection query optionals]
   (->> optionals
        (merge {"query" query})
        (app-request connection "search.all"))))

(defn files
  "Searches for files matching a query.
  Optional arguments are:
  - sort: return matches sorted by either score or timestamp
  - sort_dir: change sort direction
  - highlight: enable query highlight markers
  - count: number of items to return per page
  - page: page number of results to return"
  ([connection query]
   (files connection query {}))
  ([connection query optionals]
   (->> optionals
        (merge {"query" query})
        (app-request connection "search.files"))))

(defn messages
  "Searches for messages matching a query.
  Optional arguments are:
  - sort: return matches sorted by either score or timestamp
  - sort_dir: change sort direction
  - highlight: enable query highlight markers
  - count: number of items to return per page
  - page: page number of results to return"
  ([connection query]
   (messages connection query {}))
  ([connection query optionals]
   (->> optionals
        (merge {"query" query})
        (app-request connection "search.messages"))))

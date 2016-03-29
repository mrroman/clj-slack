(ns clj-slack.users
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [list]))

(defn get-presence
  "Gets user presence information."
  [connection user-id]
  (app-request connection "users.getPresence" {"user" user-id}))

(defn info
  "Gets information about a user."
  [connection user-id]
  (app-request connection "users.info" {"user" user-id}))

(defn list
  "Lists all users in a Slack team."
  [connection]
  (app-request connection "users.list"))

(defn set-active
  "Marks a user as active."
  [connection]
  (app-request connection "users.setActive"))

(defn set-presence
  "Manually sets user presence."
  [connection user-id presence]
  (app-request connection "users.setPresence" {"user" user-id "presence" presence}))

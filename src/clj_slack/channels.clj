(ns clj-slack.channels
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [list]))

(defn archive
  "Archives a channel."
  [connection channel-id]
  (app-request connection "channels.archive" {"channel" channel-id}))

(defn create
  "Creates a channel."
  [connection name]
  (app-request connection "channels.create" {"name" name}))

(defn history
  "Fetches history of messages and events from a channel.
  Optional arguments are:
  - latest: end of time range of messages to include in results
  - oldest: start of time range of messages to include in results
  - inclusive: include messages with latest or oldest timestamp in results
  - count: number of messages to return"
  ([connection channel-id]
   (history connection channel-id {}))
  ([connection channel-id optionals]
   (->> optionals
        (merge {"channel" channel-id})
        (app-request connection "channels.history"))))

(defn info
  "Gets information about a channel."
  [connection channel-id]
  (app-request connection "channels.info" {"channel" channel-id}))

(defn invite
  "Invites a user to a channel."
  [connection channel-id user-id]
  (app-request connection "channels.invite" {"channel" channel-id "user" user-id}))

(defn join
  "Joins a channel, creating it if needed."
  [connection channel-name]
  (app-request connection "channels.join" {"name" channel-name}))

(defn kick
  "Removes a user from a channel."
  [connection channel-id user-id]
  (app-request connection "channels.kick" {"channel" channel-id "user" user-id}))

(defn leave
  "Leaves a channel."
  [connection channel-id]
  (app-request connection "channels.leave" {"channel" channel-id}))

(defn list
  "List channels.
  Optional argument:
  - exclude_archived: don't return archived channels"
  ([connection]
   (list connection {}))
  ([connection optionals]
   (app-request connection "channels.list" optionals)))

(defn mark
  "Sets the read cursor in a channel."
  [connection channel-id timestamp]
  (app-request connection "channels.mark" {"channel" channel-id "ts" timestamp}))

(defn rename
  "Rename a channel."
  [connection channel-id name]
  (app-request connection "channels.rename" {"channel" channel-id "name" name}))

(defn set-purpose
  "Sets the purpose for a channel."
  [connection channel-id purpose]
  (app-request connection "channels.setPurpose" {"channel" channel-id "purpose" purpose}))

(defn set-topic
  "Sets the topic for a channel."
  [connection channel-id topic]
  (app-request connection "channels.setTopic" {"channel" channel-id "topic" topic}))

(defn unarchive
  "Unarchives a channel."
  [connection channel-id]
  (app-request connection "channels.unarchive" {"channel" channel-id}))

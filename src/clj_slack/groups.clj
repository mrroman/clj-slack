(ns clj-slack.groups
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [list]))

(defn archive
  "Archives a private group."
  [connection channel-id]
  (app-request connection "groups.archive" {"channel" channel-id}))

(defn close
  "Closes a private group."
  [connection channel-id]
  (app-request connection "groups.close" {"channel" channel-id}))

(defn create
  "Creates a private group."
  [connection name]
  (app-request connection "groups.create" {"name" name}))

(defn create-child
  "Clones and archives a private group."
  [connection channel-id]
  (app-request connection "groups.createChild" {"channel" channel-id}))

(defn history
  "Fetches history of messages and events from a private group.
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
        (app-request connection "groups.history"))))

(defn info
  "Gets information about a private channel"
  [connection channel-id]
  (app-request connection "groups.info" {"channel" channel-id}))

(defn invite
  "Invites a user to a private group."
  [connection channel-id user-id]
  (app-request connection "groups.invite" {"channel" channel-id "user" user-id}))

(defn kick
  "Removes a user from a private group."
  [connection channel-id user-id]
  (app-request connection "groups.kick" {"channel" channel-id "user" user-id}))

(defn leave
  "Leaves a private group."
  [connection channel-id]
  (app-request connection "groups.leave" {"channel" channel-id}))

(defn list
  "Lists private groups that the calling user has access to.
  Optional argument:
  - exclude_archived: don't return archived groups"
  ([connection]
   (list connection {}))
  ([connection optionals]
   (app-request connection "groups.list" optionals)))

(defn mark
  "Sets the read cursor in a private group."
  [connection channel-id timestamp]
  (app-request connection "groups.mark" {"channel" channel-id "ts" timestamp}))

(defn open
  "Opens a private group."
  [connection channel-id]
  (app-request connection "groups.open" {"channel" channel-id}))

(defn rename
  "Rename a private group."
  [connection channel-id name]
  (app-request connection "groups.rename" {"channel" channel-id "name" name}))

(defn set-purpose
  "Sets the purpose for a private group."
  [connection channel-id purpose]
  (app-request connection "groups.setPurpose" {"channel" channel-id "purpose" purpose}))

(defn set-topic
  "Sets the topic for a private group."
  [connection channel-id topic]
  (app-request connection "groups.setTopic" {"channel" channel-id "topic" topic}))

(defn unarchive
  "Unarchives a private group."
  [connection channel-id]
  (app-request connection "groups.unarchive" {"channel" channel-id}))

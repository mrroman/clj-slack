(ns clj-slack.reactions
  (:require [clj-slack.core :refer [app-request]]))

(defn add
  "Adds a reaction to an item.
  This API adds reaction(emoji) to an item.
  One of file, file_comment, or the combination of channel and timestamp must be specified.
  Optional arguments are:
  - file: file to add reaction to
  - file_comment: file comment to add reaction to
  - channel: channel where the message to add reaction to was posted
  - timestamp: timestamp of the message to add reaction to"
  [connection name optionals]
  (->> optionals
       (merge {"name" name})
       (app-request connection "reactions.add")))

(defn get
  "Gets reactions for an item.
  - file: file to get reactions to
  - file_comment: file comment to get reactions to
  - channel: channel where the message to get reactions to was posted
  - timestamp: timestamp of the message to get reactions to
  - full: if true always return the complete reaction list"
  [connection optionals]
  (app-request connection "reactions.get" optionals))

(defn list
  "Lists reactions made by a user.
  - user: show reactions made by this user. Defaults to the authed user
  - full: if true always return the complete reaction list
  - count: number of items to return per page
  - page: page number of results to return."
  [connection optionals]
  (app-request connection "reactions.list" optionals))

(defn remove
  "Removes a reaction from an item.
  - file: file to remove reaction from
  - file_comment: file comment to remove reaction from
  - channel: channel where the message to remove reaction from was posted
  - timestamp: timestamp of the message to remove reaction from"
  [connection name optionals]
  (->> optionals
       (merge {"name" name})
       (app-request connection "reactions.add")))

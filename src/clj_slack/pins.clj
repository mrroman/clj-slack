(ns clj-slack.pins
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [list remove]))

(defn add
  "Pins an item to a channel (file, file comment, channel message, or group message)
  The channel argument is required and one of file, file_comment, or timestamp must also be specified.
  Optional arguments are :
  - file: file to pin
  - file_comment: file comment to pin
  - timestamp: timestamp of the message to pin"
  [connection channel-id optionals]
  (->> optionals
       (merge {"channel" channel-id})
       (app-request connection "pins.add")))

(defn list
  "List items pinned to a channel."
  [connection channel-id]
  (app-request connection "pins.list" {"channel" channel-id}))

(defn remove
  "Un-Pins an item to a channel (file, file comment, channel message, or group message)
  The channel argument is required and one of file, file_comment, or timestamp must also be specified.
  Optional arguments are :
  - file: file to un-pin
  - file_comment: file comment to un-pin
  - timestamp: timestamp of the message to un-pin"
  [connection channel-id optionals]
  (->> optionals
       (merge {"channel" channel-id})
       (app-request connection "pins.remove")))

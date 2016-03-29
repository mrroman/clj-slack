(ns clj-slack.rtm
  (:require [clj-slack.core :refer [bot-request]]))

(defn start
  "Starts a Real Time Messaging session."
  [connection]
  (bot-request connection "rtm.start"))

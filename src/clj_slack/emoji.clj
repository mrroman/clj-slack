(ns clj-slack.emoji
  (:require [clj-slack.core :refer [app-request]])
  (:refer-clojure :exclude [list]))

(defn list
  "Lists custom emoji for a team."
  [connection]
  (app-request connection "emoji.list"))

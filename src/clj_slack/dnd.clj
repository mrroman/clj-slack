(ns clj-slack.dnd
  (:require [clj-slack.core :refer [app-request]]))

(defn info
  "Retrieves a user's current Do Not Disturb status."
  [connection user]
  (app-request connection "dnd.info" {"user" user}))

(defn set-snooze
  "Turns on Do Not Disturb mode for the current user, or changes its duration."
  [connection num_minutes]
  (app-request connection "dnd.setSnooze" {"num_minutes" (.toString num_minutes)}))

(defn end-snooze
  "Ends the current user's snooze mode immediately."
  [connection]
  (app-request connection "dnd.endSnooze"))

(defn end-dnd
  "Ends the current user's Do Not Disturb session immediately."
  [connection]
  (app-request connection "dnd.endDnd"))

(defn team-info
  "Retrieves the Do Not Disturb status for users on a team."
  ([connection]
   (team-info connection {}))
  ([connection optionals]
   (app-request connection "dnd.teamInfo" optionals)))

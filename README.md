# clj-slack

clj-slack is a Clojure library to talk to the [Slack](http://slack.com) REST API. It supports almost the entire Slack API.

![Build Status](https://travis-ci.org/julienXX/clj-slack.svg?branch=master)

## Documentation

Slack API methods are described [here](https://api.slack.com/methods).

clj-slack documentation is available [here](http://julienblanchard.com/clj-slack/).

## Usage

This is on [Clojars](https://clojars.org/mrroman/clj-slack). Just add ```[mrroman/clj-slack "0.6.0-SNAPSHOT"]``` to your ```:dependencies``` in your project.clj file.

Get your access token [here](https://api.slack.com/web).

Your need to create a connection with ```(clj-slack.core/connection "https://slack.com/api" {:app "APP TOKEN" :bot "BOT TOKEN"})``` and pass it as the first argument of every functions in clj-slack. Almost every call needs APP TOKEN. Only rtm/start needs BOT TOKEN. You can use your test token for both of these tokens. Of course you can change api-url for debugging or testing purposes.

clj-slack will throw an Exception if the connection map you're trying to use is not valid.

Example:
```clojure
(require 'clj-slack.core)
(require 'clj-slack.users)

(def connection (clj-slack.core/connection "https://slack.com/api" {:app "APP TOKEN" :bot "BOT TOKEN"})
(clj-slack.users/list connection)
```

You can use optional params described in [Slack API](https://api.slack.com/methods) by passing them through a map.
```clojure
(require 'clj-slack.core)
(require 'clj-slack.stars)

(def connection (clj-slack.core/connection "https://slack.com/api" {:app "APP TOKEN" :bot "BOT TOKEN"})
(clj-slack.stars/list connection {:count "2" :page "3"})
```

Uploading a file:
```clojure
(require 'clj-slack.core)
(require 'clj-slack.files)

(def connection (clj-slack.core/connection "https://slack.com/api" {:app "APP TOKEN" :bot "BOT TOKEN"})
(clj-slack.files/upload connection (clojure.java.io/input-stream "/path/to/file/file.ext") {:channels "CHANNEL_ID", :title "This is a file.})
```

## License

Copyright (C) Julien Blanchard, Konrad Mrożek

Distributed under the Eclipse Public License, the same as Clojure.

(ns clj-slack.core
  (:require [cheshire.core :as cheshire]
            [clj-http
             [client :as http]
             [conn-mgr :as conn-mgr]]))

;; Connection

(defrecord Connection [api-url tokens error-handler log-handler pool])

(defn- default-error-handler
  "Error handler for all slack requests. It takes error msg
  and the context of an error. Can be redefined with bind."
  [msg ctx]
  (throw (ex-info (str "Error on Slack API request: " msg) ctx)))

(defn- verify-api-url
  [api-url]
  (assert
   (and (string? api-url)
        (re-find #"^https?:\/\/" api-url))
   (str "clj-slack: API URL is not valid. :api-url has to be a valid URL (https://slack.com/api usually), but is " (pr-str api-url))))

(defn create-connection
  "Creates connection used for all Slack API functions."
  ([api-url tokens]
   (create-connection api-url tokens {}))
  ([api-url tokens {:keys [error-handler log-handler pool]
                    :or {error-handler default-error-handler
                         log-handler println
                         pool (conn-mgr/make-reusable-conn-manager {:timeout 2
                                                                    :thread 3})}}]
   (verify-api-url api-url)
   (->Connection api-url tokens error-handler log-handler pool)))

(defn close
  "Closes connection to Slack."
  [connection]
  (when-let [cm (:pool connection)]
    (clj-http.conn-mgr/shutdown-manager cm)))

;; HTTP calls

(defn- endpoint-url
  "Returns full URL of Slack method"
  [connection endpoint]
  (str (:api-url connection) "/" endpoint))

(defn- stringify-keys
  "Creates a new map whose keys are all strings."
  [m]
  (into {} (for [[k v] m]
             (if (keyword? k)
               [(name k) v]
               [(str k) v]))))

(defn- send-request
  "Sends a GET http request with formatted params"
  [connection url params]
  (http/get url {:query-params (stringify-keys params)
                 :connection-manager (:pool connection)}))

(defn- build-multiparts
  "Builds an http-kit multiparts sequence"
  [params]
  (for [[k v] params]
    (if (instance? java.io.File v)
      {:name k :content v :filename (.getName v) :encoding "UTF-8"}
      {:name k :content v :encoding "UTF-8"})))

(defn- send-post-request
  "Sends a POST http request with formatted params"
  [connection url params]
  (http/post url {:multipart (->> params
                                  stringify-keys
                                  build-multiparts)
                  :connection-manager (:pool connection)}))

;; Slack calls

(defn- handle-http-response [response connection]
  (if-let [body (:body response)]
    (cheshire/parse-string body true)
    ((:error-handler connection) (:error response) {:response response})))

(defn- handle-slack-response [response connection endpoint query]
  (if (:ok response)
    response
    ((:error-handler connection) (:error response) {:endpoint endpoint
                                                    :query query
                                                    :response response})))

(defn slack-request
  ([sender connection endpoint]
   (slack-request connection sender endpoint {}))
  ([sender connection endpoint query]
   (assert (instance? Connection connection))
   ((:log-handler connection) endpoint query)
   (let [response (sender connection
                          (endpoint-url connection endpoint)
                          (stringify-keys query))]
     (-> response
         (handle-http-response connection)
         (handle-slack-response connection endpoint query)))))

;; Authorized requests

(defn- authorized-request
  ([token-key sender connection endpoint]
   (authorized-request token-key sender connection endpoint {}))
  ([token-key sender connection endpoint query]
   (let [token (some-> connection :tokens token-key)]
     (assert token (str token-key " not defined in connection tokens"))
     (slack-request sender connection endpoint (assoc query :token token)))))

(def app-request
  "Request which will be authorized with :app token"
  (partial authorized-request :app send-request))

(def app-post-request
  "Multipart request which will be authorized with :app token"
  (partial authorized-request :app send-post-request))

(def bot-request
  "Request which will be authorized with :bot token"
  (partial authorized-request :bot send-request))

(def bot-post-request
  "Multipart request which will be authorized with :bot token"
  (partial authorized-request :bot send-post-request))

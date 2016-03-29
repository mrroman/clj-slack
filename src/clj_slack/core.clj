(ns clj-slack.core
  (:require
   [clj-http.client :as http]
   [cheshire.core :as cheshire]))

(defn- verify-api-url
  [connection]
  (assert
   (and (string? (:api-url connection))
        (and (seq (:api-url connection))
             (not (nil? (re-find #"^https?:\/\/" (:api-url connection))))))
   (str "clj-slack: API URL is not valid. :api-url has to be a valid URL (https://slack.com/api usually), but is " (pr-str (:api-url connection)))))

(defn- endpoint-url
  "Returns full URL of Slack method"
  [connection endpoint]
  (verify-api-url connection)
  (str (:api-url connection) "/" endpoint))

(defn- send-request
  "Sends a GET http request with formatted params"
  [url params]
  (let [response (http/get url {:query-params params})]
    (if-let [body (:body response)]
      (cheshire/parse-string body true)
      (*error-handler* (:error response)))))

(defn- send-post-request
  "Sends a POST http request with formatted params"
  [url multiparts]
  (let [response (http/post url {:multipart multiparts})]
    (cheshire/parse-string (:body response) true)))

(defn- build-multiparts
  "Builds an http-kit multiparts sequence"
  [params]
  (for [[k v] params]
    (if (instance? java.io.File v)
      {:name k :content v :filename (.getName v) :encoding "UTF-8"}
      {:name k :content v :encoding "UTF-8"})))

(defn stringify-keys
  "Creates a new map whose keys are all strings."
  [m]
  (into {} (for [[k v] m]
             (if (keyword? k)
               [(name k) v]
               [(str k) v]))))

(defn slack-request
  ([connection endpoint]
   (slack-request connection endpoint {}))
  ([connection endpoint query]
   (let [url (endpoint-url connection endpoint)]
     (send-request url (stringify-keys query)))))

(defn slack-post-request
  [connection endpoint post-params]
  (let [url (endpoint-url connection endpoint)
        multiparts-params (->> post-params
                               stringify-keys
                               build-multiparts)]
    (send-post-request url multiparts-params)))

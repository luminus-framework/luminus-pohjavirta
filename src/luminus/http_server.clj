(ns luminus.http-server
  (:require [clojure.tools.logging :as log]
            [clojure.set :refer [rename-keys]]
            [pohjavirta.server :as http-server]))

(defn format-options [opts]
  (merge
    {:host "0.0.0.0"}
    (dissoc opts :handler :path :contexts)))

(defn start [{:keys [handler port] :as opts}]
  (try
    (let [server (http-server/create handler (format-options opts))]
      (http-server/start server)
      (log/info "server started on port" port)
      server)
    (catch Throwable t
      (log/error t (str "server failed to start on port: " port)))))

(defn stop [server]
  (http-server/stop server)
  (log/info "HTTP server stopped"))

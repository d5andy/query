(ns query.core
  (:require [clojure.java.jdbc :refer :all]
            [yesql.core :refer [defquery]]
            [environ.core :refer [env]]))

;; database configuration
(def my-db {:classname   (env :classname)
            :subprotocol (env :subprotocol)
            :subname     (env :subname)})

;; liquibase
(def database
  (-> (liquibase.database.DatabaseFactory/getInstance)
      (.findCorrectDatabaseImplementation
       (liquibase.database.jvm.JdbcConnection.
        (clojure.java.jdbc/get-connection my-db)))))
(def liquibase (liquibase.Liquibase.
                "db/schema.yaml"
                (liquibase.resource.ClassLoaderResourceAccessor.)
                database))
(.update liquibase "app_start")
(.close database)

;; insert dummy data
(insert! my-db :news {:date "2011-9-12",
                      :url "http://example.com",
                      :title "We should write games",
                      :body "Example using SQLite with Clojure"})

;; ysql databasex
(defquery all-news "db/all-news.sql" {:connection my-db})

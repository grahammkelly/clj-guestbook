(ns guestbook.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager)
  ;; (:import java.util.Date)
)

(defn db-name []
  "db.sq3")

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname (db-name)})

(defn create-guestbook-table []
  (sql/with-connection db
    (sql/create-table :guestbook
                      [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
                      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
                      [:name "TEXT"]
                      [:message "TEXT"]
    )
    (sql/do-commands "CREATE INDEX guestbook_timestamp_idx ON guestbook (timestamp)")))

(defn read-guest-comments []
  (sql/with-connection db
    (sql/with-query-results res
      ["SELECT * FROM guestbook ORDER BY timestamp DESC"]
      (doall res)  ;;Returned results are lazy unless 'doall...'
    )))

(defn save-guest-comment [name message]
  (sql/with-connection db
    (sql/insert-values :guestbook
                       [:name :message]
                       [name message])))


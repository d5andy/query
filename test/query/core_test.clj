(ns query.core-test
  (:require [clojure.test :refer :all]
            [query.core :refer :all]
            [clojure.java.jdbc :refer :all]))
(comment
  (defn create-db []
    (try (db-do-commands my-db
                         (create-table-ddl :news
                                           [:date :text]
                                           [:url :text]
                                           [:title :text]
                                           [:body :text]))
         (catch Exception e (println e))))

  (defn delete-db []
    (try (db-do-commands my-db
                         (drop-table-ddl :news
                                         [:date :text]
                                         [:url :text]
                                         [:title :text]
                                         [:body :text]))
         (catch Exception e (println e)))))

(deftest airth
  (testing "lala" (is (= 2 (+ 1 1)))))

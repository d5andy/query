(set-env!
 :resource-paths #{ "resources" "src/cljs" "src/clj" }
 :dependencies '[;; boot
                 [adzerk/boot-test "1.1.1" :scope "test"]
                 [adzerk/boot-reload "0.4.5" :scope "test"]
                 [adzerk/boot-cljs "1.7.228-1"]
                 [adzerk/boot-cljs-repl   "0.3.0"] ;; latest release
                 [pandeiro/boot-http "0.7.3"]
                 [environ "1.0.2"]
                 [boot-environ "1.0.2"]
                 ;; expectations
                 [expectations "2.0.9" :scope "test"]
                 ;; clojure core
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript   "1.7.228"]
                 ;; html
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [enlive "1.1.6"]
                 ;; db
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 ;; sql
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.liquibase/liquibase-core "3.4.2"]
                 [org.yaml/snakeyaml "1.16"]
                 [yesql "0.5.1"]
                 ;; emacs
                 [cider/cider-nrepl "0.8.2"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [weasel                  "0.7.0"  :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                 ;; webdriver
                 [clj-webdriver "0.7.2" :scope "test"]
                 [org.seleniumhq.selenium/selenium-java "2.47.1" :scope "test"]
                 [org.seleniumhq.selenium/selenium-chrome-driver "2.47.1" :scope "test"]])

(require '[adzerk.boot-test :refer :all]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[pandeiro.boot-http    :refer [serve]]
         '[environ.boot :refer [environ]])

(def disk {:classname   "org.sqlite.JDBC"
           :subprotocol "sqlite"
           :subname     "database"})
(def inmem {:classname   "org.sqlite.JDBC"
            :subprotocol "sqlite"
            :subname     "mem:testdb"})

(deftask aot-jar "compile the source code"
  []
  (comp (aot "-a") (jar) ))

(deftask testing
  "Profile setup for running tests."
  []
  (merge-env! :resource-paths #{"test"}))

(deftask runTests
  []
  (comp (environ :env disk)
        (serve :handler 'query.app/app)
        (test :junit-output-to "reports/")))

(deftask dev "start up a dev env with the ring server and reloading"
  []
  (comp (environ :env disk)
        (serve :handler 'query.app/app :reload true)
        (watch)
        (speak)
        (reload)
        (cljs-repl)
        (cljs)))

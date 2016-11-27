(ns query.core-web
  (:require
   [clj-webdriver.taxi :refer :all]
   [clojure.test :refer :all]))

(deftest verify_the_title_of_the_page
  (System/setProperty "webdriver.chrome.driver" "./support/chromedriver-mac")
  (set-driver! {:browser :chrome} )
  (to "http://localhost:3000/index-plus")
  (is (= "Figwheel template" (text "h2")))
  (quit))

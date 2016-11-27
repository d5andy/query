(ns query.app
  (:require [query.core :refer [all-news]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [net.cgrand.enlive-html :as enlive]))

(def index (enlive/html-resource "html/index.html"))

(enlive/deftemplate table-plus "html/table.html" [tabledata]
  [:tr.bookmark] (enlive/clone-for [row tabledata]
                                   [:td.title] (enlive/content (:title row))
                                   [:td.url] (enlive/content (:url row))))

(enlive/deftemplate index-plus "html/index.html" [content_]
  [#{:p}] (enlive/content content_)
  [:div#table] (enlive/content [{:tr "la"}]))

(defroutes app
  (GET "/" [] (enlive/emit* index))
  (GET "/index-plus" [] (index-plus "PPPLLLSSS"))
  (GET "/table-plus" [] (table-plus (all-news)))
  (route/resources "/" )
  (route/resources "/public") ;; just for the reload of it
  (route/not-found "<h1>Page not found</h1>"))

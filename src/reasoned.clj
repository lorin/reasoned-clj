(ns reasoned
  (:require [nextjournal.clerk :as clerk]))

  
(defn -main
  []
  (clerk/serve! {:browse? true})
  )
(ns reasoned
  (:require [nextjournal.clerk :as clerk]))

  
(defn -main []
  (clerk/serve! {:watch-paths ["notebooks"], :browse? true})
  )
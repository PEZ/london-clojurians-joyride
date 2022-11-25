(ns scrap
  (:require ["vscode" :as vscode]
            [joyride.core :as joy]))

(defn open-wat []
  (.appendLine (joy/output-channel) "Opening education")
  (vscode/commands.executeCommand "simpleBrowser.show"
                                  (str "https://" (+ 0.1 0.2) ".com")))

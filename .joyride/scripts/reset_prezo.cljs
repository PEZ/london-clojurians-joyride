(ns reset-prezo
  (:require ["vscode" :as vscode]
            [promesa.core :as p])) 

(def zen-config (vscode/workspace.getConfiguration "zenMode"))
(def wb-config (vscode/workspace.getConfiguration "workbench"))
(def window-config (vscode/workspace.getConfiguration "window"))
(def md-preview-config (vscode/workspace.getConfiguration "markdown.preview"))
(def editor-config (vscode/workspace.getConfiguration "editor"))

(vscode/commands.executeCommand "editor.action.fontZoomReset")

(.update zen-config "hideStatusBar" false)
(.update window-config "zoomLevel" 3)
(.update wb-config "statusBar.visible" true)
(.update md-preview-config "fontSize" 24)
(.update editor-config "fontSize" 18)

(p/let [answer (vscode/window.showInformationMessage
                "Is it Showtime?" "YES" "NO") 
        showtime? (= "YES" answer)]
  (.update md-preview-config "doubleClickToSwitchToEditor" (not showtime?)))

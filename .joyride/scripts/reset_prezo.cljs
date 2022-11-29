(ns reset-prezo
  (:require ["vscode" :as vscode]
            [promesa.core :as p])) 

(def zen-config (vscode/workspace.getConfiguration "zenMode"))
(.update zen-config "hideStatusBar" false)
(.update zen-config "silentNotifications" false)
(.update zen-config "centerLayout" false)

(def wb-config (vscode/workspace.getConfiguration "workbench"))
(.update wb-config "statusBar.visible" true)

(def window-config (vscode/workspace.getConfiguration "window"))
(.update window-config "zoomLevel" 3)

(def md-preview-config (vscode/workspace.getConfiguration "markdown.preview"))
(.update md-preview-config "fontSize" 24)
(comment 
  (.update md-preview-config "doubleClickToSwitchToEditor" true)
  :rcf)

(def editor-config (vscode/workspace.getConfiguration "editor"))
(.update editor-config "fontSize" 18)
(vscode/commands.executeCommand "editor.action.fontZoomReset")

(p/let [answer (vscode/window.showInformationMessage
                "Is it Showtime?" "YES" "NO")
        showtime? (= "YES" answer)]
  (.update md-preview-config "doubleClickToSwitchToEditor" (not showtime?))
  #_(when showtime?
    (let [ws-root-uri (-> vscode/workspace.workspaceFolders first .-uri)
          doc-uri (vscode/Uri.joinPath ws-root-uri "./slides/more-examples.md")
          backup-uri (vscode/Uri.joinPath ws-root-uri "./etc/more-examples-backup.md")]
      (-> (p/do (vscode/workspace.fs.rename doc-uri
                                            backup-uri
                                            #js {:overwrite true}))
          (p/catch (fn [e]
                     (def result result)
                     (vscode/window.showErrorMessage (.-message e))))))))

"Hello London Clojurians! 🥰"
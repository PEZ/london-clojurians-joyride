(ns scrap
  (:require ["vscode" :as vscode]
            [joyride.core :as joy]
            [clojure.edn :as edn]))

(defn open-wat []
  (.appendLine (joy/output-channel) "Opening education")
  (vscode/commands.executeCommand "simpleBrowser.show"
                                  (str "https://" (+ 0.1 0.2) ".com")))

(require '[promesa.core :as p])
(p/let [greetee (vscode/window.showInputBox
                 #js {:title "Joyride Greeter"
                      :prompt "Who do you want to greet today?"
                      :placeHolder "My wife. All my patrons. Mahatma Gandhi..."})
        button (vscode/window.showInformationMessage (str "Hello " greetee "! ♥️") "OK" "Fabulous")
        right-back-at-you (if button
                            button
                            "boring")]
  (vscode/window.showInformationMessage 
   (str "You are " right-back-at-you "! 😀")))


  ;; Toggle status bar via command
  (vscode/commands.executeCommand "workbench.action.toggleStatusbarVisibility")

(require '[promesa.core :as p]
         '[next-slide])

(let [ws-root-uri (-> vscode/workspace.workspaceFolders first .-uri)
      backup-uri (vscode/Uri.joinPath ws-root-uri "./etc/more-examples-original.md")
      doc-uri (vscode/Uri.joinPath ws-root-uri "./slides/more-examples.md")]
  (-> (p/do (vscode/workspace.fs.copy backup-uri
                                      doc-uri
                                      #js {:overwrite false}) 
            (next-slide/next! false)
            (next-slide/next!)
            (vscode/window.showInformationMessage "Phew! 😅"))
      (p/catch (fn [e]
                 (def result result)
                 (vscode/window.showErrorMessage (.-message e))))))
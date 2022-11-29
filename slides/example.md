# Hello VS Code

<div class="slide scroll-y fz-90">

Two (silly) examples:
``` clojure
(require '["vscode" :as vscode]
         '[promesa.core :as p])
;; 1
(-> (vscode/window.showInformationMessage (str (+ 0.1 0.2))
                                          "WAT?")
    (.then (fn [button]
             (when-not (nil? button)
               (vscode/commands.executeCommand
                "simpleBrowser.show"
                (str "https://" (+ 0.1 0.2) ".com"))))))
;; 2
(p/let [ws-root (-> vscode/workspace.workspaceFolders first .-uri)
        examples (vscode/Uri.joinPath ws-root 
                                      ".joyride/scripts/london_examples.cljs")
        doc (vscode/workspace.openTextDocument examples)]
  (vscode/window.showTextDocument doc))

```
<div>
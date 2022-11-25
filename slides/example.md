# Hello VS Code

<div class="slide scroll-y fz-90">

``` clojure
(require '["vscode" :as vscode])

(-> (vscode/window.showInformationMessage (str (+ 0.1 0.2))
                                          "WAT?")
    (.then (fn [button]
             (when-not (nil? button)
               (vscode/commands.executeCommand
                "simpleBrowser.show"
                (str "https://" (+ 0.1 0.2) ".com"))))))

(def ws-root (-> vscode/workspace.workspaceFolders first .-uri))
(def examples (vscode/Uri.joinPath ws-root ".joyride/scripts/london_examples.cljs"))
(-> (vscode/workspace.openTextDocument examples)
    (.then #(vscode/window.showTextDocument %)))

```
<div>
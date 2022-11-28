# Moar Joyride Examples

<div class="slide">
<div class="fw fz-80">

```clojure
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
```
</div>
</div><!--slide-->
<div class="footer">

Slides and other material: https://github.com/PEZ/london-clojurians-joyride
</div>
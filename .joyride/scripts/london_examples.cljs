(ns london-examples
  (:require ["vscode" :as vscode]
            [joyride.core :as joy]
            [promesa.core :as p]))



(comment
  ;; Zen Mode Status bar
  (.get (vscode/workspace.getConfiguration "zenMode") "hideStatusBar")
  (-> (vscode/workspace.getConfiguration "zenMode")
      (.update "hideStatusBar" false vscode/ConfigurationTarget.Workspace))

  ;; Toggle editor line numbers
  (set! (.-lineNumbers vscode/window.activeTextEditor.options)
        ({1 0 0 1} (.-lineNumbers vscode/window.activeTextEditor.options)))

  ;; Write to the Joyride Output channel
  (.appendLine (joy/output-channel) "Hello London Clojurians! ♥️")

  ;; Create a statusbar item
  (def item (vscode/window.createStatusBarItem
             vscode/StatusBarAlignment.Right
             1000))

  (set! (.-text item) "0.1 + 0.2")

  (.show item)

  (set! (.-tooltip item) "Educate yourself")

  ;; Make it a button
  (set! (.-command item)
        (clj->js
         {:command "joyride.runCode"
          :arguments [(str
                       '(:require '["vscode" :as vscode]
                                  '[joyride.core :as joy])
                       '(.appendLine (joy/output-channel)
                                     "Opening education")
                       '(vscode/commands.executeCommand
                         "simpleBrowser.show"
                         (str "https://" (+ 0.1 0.2) ".com")))]}))
  ;; Would be simpler to use the command `simpleBrowser.show` here
  ;; Except, the logging side effect

  (.hide item)

  (.show item)

  (.dispose item)

  ;; Paint the item  
  (def gold "#FFD700")

  (set! (.-color item) gold)









  ;; Update the color alpha

  (def !alpha (atom 240))

  (defn nudge-color! []
    (let [color gold
          alpha (* 255
                   (/ (+ (js/Math.cos (* 2
                                         js/Math.PI
                                         (/ @!alpha 255)))
                         1)
                      2))]
      (swap! !alpha (comp inc inc inc))
      (str color (-> alpha
                     int
                     js/Number.
                     (.toString 16)
                     (.padStart 2 "0")))))

  (nudge-color!)
  @!alpha
  (reset! !alpha 1)
  (reset! !alpha 64)
  (reset! !alpha 125)
  (reset! !alpha 191)
  (reset! !alpha 255)

  (defn nudge-item! []
    (set! (.-color item) (nudge-color!)))

  (nudge-item!)




  ;; Bind keyboard shortcuts to Joyride code
  ;;  {
  ;;      "command": "joyride.runCode",
  ;;      "args": "(london-examples/nudge-item!)",
  ;;      "key": "space",
  ;;      "when": "london:nudgeEnabled"
  ;;  },


  ;; Set some context
  (vscode/commands.executeCommand "setContext"
                                  "london:nudgeEnabled" true)
  ;; {
  ;;   "command": "paredit.deleteBackward",
  ;;   "key": "backspace",
  ;;   "when": "calva:keybindingsEnabled && editorLangId == clojure && editorTextFocus && paredit:keyMap == strict && !editorReadOnly && !editorHasMultipleSelections && !calva:cursorInComment"
  ;; };; 

  ;; Maybe not nudge via keyboard shortcut?







  ;; Bring <blink> back!
  (defonce !interval-ids (atom []))
  (swap! !interval-ids conj (js/setInterval nudge-item! 20))
  (js/clearInterval (peek @!interval-ids))
  @!interval-ids
  (reset! !interval-ids [])





  ;; User and Workspace script
  ;; E.g. typist, next-slide

  ;; User and Workspace activation scripts






  ;; Require Extension APIs

  (require '["ext://betterthantomorrow.calva$v0" :as calva])

  (vscode/commands.executeCommand
   "simpleBrowser.show"
   "https://calva.io/api/#editorreplace")

  (-> (p/let [top-level-form-range (first (calva/ranges.currentTopLevelForm))
              _ (calva/editor.replace vscode/window.activeTextEditor top-level-form-range "Some new text")]
        (println "Text replaced!"))
      (p/catch (fn [e]
                 (println "Error replacing text:" e))))




  ;; Require from NPM

  (require '["posthtml-parser" :as parser]
           '[clojure.walk :as walk])

  (defn html->hiccup
    [html]
    (-> html
        (parser/parser)
        (js->clj :keywordize-keys true)
        (->> (into [:div])
             (walk/postwalk
              (fn [{:keys [tag attrs content] :as element}]
                (if tag
                  (into [(keyword tag) (or attrs {})] content)
                  element))))))

  (comment
    (html->hiccup "<label for=\"hw\">Foo</label><ul id=\"foo\"><li>Hello</li></ul>")
    :rcf)

  (require '["open" :refer [open]])
  (open "https://github.com/BetterThanTomorrow/joyride/blob/master/doc/api.md")









  :rcf)

"♥️ Hello London Clojurians! ♥️"

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
      backup-uri (vscode/Uri.joinPath ws-root-uri "./etc/more-examples-backup.md")
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
  
  
(require '[reagent.core :as r]
                 '[reagent.dom :as rdom])

(def state (r/atom {:clicks 0}))

(defn my-component []
  (let [clicks (:clicks @state)]
    [:div
     [:p "Clicks: " clicks]
     [:p [:button {:on-click #(swap! state update :clicks inc)}
          "Click me!"]]
     [:div
      [:img {:src "https://raw.githubusercontent.com/babashka/sci/61d92ad3f08f83568fb25301c80fbd591f02f96b/logo/logo.svg"
             :style {:width "100%"
                     :transform (str "rotate(" clicks "deg)")}}]
      [:h2 {:style {:text-align :center
                    :font-family "Fira Sans"
                    :font-size "30pt"
                    :white-space :pre-wrap}}
       "Small\nClojure\nInterpreter"]]]))

(rdom/render [my-component] (.getElementById js/document "app"))
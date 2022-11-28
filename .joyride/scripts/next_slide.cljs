(ns next-slide
  (:require ["vscode" :as vscode]
            [promesa.core :as p]
            [clojure.edn :as edn]))

(def ^:private !state (atom {:active? false
                             :active-slide 0}))

(defn- ws-root []
  (if (not= js/undefined
            vscode/workspace.workspaceFolders)
    (.-uri (first vscode.workspace.workspaceFolders))
    (vscode/Uri.parse ".")))

(defn next!
  ([]
   (next! true))
  ([forward?] 
   (p/let [config-uri (vscode/Uri.joinPath (ws-root) "slides.edn")
           config-data (vscode/workspace.fs.readFile config-uri)
           config-text (-> (js/Buffer.from config-data) (.toString "utf-8"))
           config (edn/read-string config-text)
           slides (:slides config)
           next (if forward?
                  #(min (inc %) (dec (count slides)))
                  #(max (dec %) 0))]
     (swap! !state update :active-slide next)
     (vscode/commands.executeCommand "markdown.showPreview"
                                     (vscode/Uri.joinPath (ws-root)
                                                          (nth slides (:active-slide @!state)))))))

(defn toggle-active! []
  (swap! !state update :active? not)
  (vscode/commands.executeCommand "setContext" "next-slide:active" (:active? @!state))
  (vscode/window.showInformationMessage
   (str "next-slide:" (if (:active? @!state)
                        "activated"
                        "deactivated"))))

(comment
  @!state
  (next!)
  (next! false)
  (toggle-active!)
  )
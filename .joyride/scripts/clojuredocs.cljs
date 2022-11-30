(ns clojuredocs
  (:require ["ext://betterthantomorrow.calva$v0" :refer [ranges repl]]
            ["vscode" :as vscode]
            [clojure.string :as string]
            [clojure.edn :as edn]
            [joyride.core :as joyride]
            [promesa.core :as p]))

;; Look up definition of the symbol under the cursor
;; on clojuredocs.org. Opens in the VS Code integrated
;; browser.
;;
;; Adapted from @seancorfield's VS Code setup:
;; https://github.com/seancorfield/vscode-calva-setup/blob/develop/joyride/scripts/clojuredocs.cljs
;; 
;; Use with something like these keybindings:
;;    {
;;        "command": "joyride.runCode",
;;        "args": "(clojuredocs/lookup-current-form-or-selection)",
;;        "key": "ctrl+alt+c d",
;;    },
;;
;; Consider placing this script with your Joyride
;; User Scripts and require from your
;; `user_activate.cljs`. See the default activation
;; script for how to wait for Calva to be activated:
;; https://github.com/BetterThanTomorrow/joyride/blob/master/assets/getting-started-content/user/user_activate.cljs 

(defn resolve-in-repl [symbol-string]
  (-> (p/let [result (repl.evaluateCode
                      js/undefined
                      (str "(some->> \"" symbol-string "\""
                           "          symbol"
                           "          resolve"
                           "          symbol"
                           "          str)"))
              result-string (.-result result)
              qualified-symbol-string (edn/read-string result-string)]
        (or qualified-symbol-string
            (str "clojure.core/" symbol-string)))
      (p/catch #()) ; We fall back on clojure.core/<symbol-string>
      (p/then (fn [s]
                (if (empty? s)
                  (str "clojure.core/" symbol-string)
                  s)))))

(defn clojuredocs-url [symbol-string]
  (-> (p/let [resolved (resolve-in-repl symbol-string)]
        (str "https://clojuredocs.org/"
             (-> resolved
                 (string/replace "?" "%3f") ; clean up ? ! &
                 (string/replace "!" "%21")
                 (string/replace "&" "%26"))))))

(defn lookup-current-form-or-selection []
  (-> (p/let [[_ lookup] (ranges.currentForm)
              url (clojuredocs-url lookup)]
        (if url
          (vscode/commands.executeCommand "simpleBrowser.show" url)
          (vscode/window.showInformationMessage
           (str "clojuredocs.cljs, can't resolve: " lookup))))
      (p/catch (fn [e]
                 (println (str "Clojuredocs lookup error: " e))))))

(when (= (joyride/invoked-script) joyride/*file*)
  (lookup-current-form-or-selection))

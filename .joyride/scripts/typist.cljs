(ns typist
  (:require ["vscode" :as vscode]
            [promesa.core :as p]))

(defn gaussian-rand'
  []
  (let [accuracy 7]
    (loop [rand 0
           i 0]
      (if (< i accuracy)
        (recur (+ rand (.random js/Math)) (inc i))
        (/ rand accuracy)))))

(defn gaussian-rand
  [start end]
  (js/Math.floor
   (+ start (* (gaussian-rand') (+ (- end start) 1)))))

(def typing-pauses
  {:fast {:char 0
          :space 0
          :nl 350
          :description "Typed as a really fast keyboard wielder"}
   :intermediate {:char 75
                  :space 250
                  :nl 1300
                  :description "Typed as an intermediately fast typist"}
   :slow {:char 350
          :space 1000
          :nl 2500
          :description "Typed as a slow, painfully slow, typist"}})

(defn humanize-pause
  [s typing-speed]
  (cond
    (re-find #"^ |\t$" s) (gaussian-rand 0 (-> typing-speed typing-pauses :space))
    (re-find #"\s{2,}|\n" s) (gaussian-rand 0 (-> typing-speed typing-pauses :nl))
    :else (gaussian-rand 0 (-> typing-speed typing-pauses :char))))

(defn pause [pause-length s]
  (p/create
   (fn [resolve, _reject]
     (js/setTimeout
      resolve
      (humanize-pause s pause-length)))))

(def ^:private unicode-split-re (js/RegExp. "." "u"))

(defn simulate-typing
  "Chops up `new-text` in characters and then, one at a time,
   writes them to the clipboard and then pastes them. Pausing
   with a randomized distribution around `type-pause`.
   Restores clipboard afterwards."
  [text typing-speed]
  (p/let [matches  (re-seq #"\s+|\S+" text)
          words (if matches matches [])
          original-clipboard-content (vscode/env.clipboard.readText)]
    (p/run!
     (fn [word]
       (p/run!
        (fn [c]
          (p/do! (vscode/env.clipboard.writeText c)
                 (vscode/commands.executeCommand "editor.action.clipboardPasteAction")
                 (p/create
                  (fn [resolve, _reject]
                    (js/setTimeout
                     resolve
                     (humanize-pause c typing-speed))))))
        (if (re-find #"\s{2,}" word)
          [word]
          (re-seq unicode-split-re word))))
     words)
    (vscode/env.clipboard.writeText original-clipboard-content)))

(comment
  (simulate-typing "foo bar" :slow) 
  :rcf)







;; How about typing what's on the clipboard?

(defn type-clipboard
  "Types the clipboard content at `speed`"
  [speed]
  (p/let [clipboard-text (vscode/env.clipboard.readText)]
    (simulate-typing clipboard-text speed)))

"Typist loaded"
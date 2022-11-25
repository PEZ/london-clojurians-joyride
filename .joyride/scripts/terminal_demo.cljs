(ns terminal-demo
  (:require ["vscode" :as vscode]))

(comment
  ;; Start a terminal
  (def terminal (vscode/window.createTerminal "Joyride demo"))

  ;; Show it, without it capturing focus 
  (.show terminal true)
  ;; Show it,capturing focus 
  (.show terminal false)
  ;; Send a commands to it
  (.sendText terminal "echo hello bash world")
  (.sendText terminal "node")
  (.sendText terminal "console.log('hello node world')")
  (.sendText terminal ".exit")
  (.sendText terminal "exit")
  :rcf)

(ns showtime
  (:require [clojure.string :as string]
            ["vscode" :as vscode]
            [joyride.core :as joy]))

;; Keep a list of items and timers so that I can dispose of them
;; if I REPL away my references 😀
(defonce !state (atom {:items []
                       :timers []}))

(def commands {:stop {:command (str '(showtime/stop!))
                      :hint "Click to stop"}
               :start {:command (str '(showtime/start!))
                       :hint "Click to start"}
               :restart {:command (str '(showtime/start!))
                         :hint "Click to restart"}})

(defn zero-pad [x]
  (str (when (< x 10) "0") x))

(defn ms->time-str
  [ms]
  (let [days (int (/ ms (* 1000 60 60 24)))
        epoch-date (js/Date. ms)
        hours (- (.getHours epoch-date) 1)
        minutes (.getMinutes epoch-date)
        seconds (.getSeconds epoch-date)]
    (str (zero-pad days) ":"
         (zero-pad hours) ":"
         (zero-pad minutes) ":"
         (zero-pad seconds))))

(defn update-item! []
  (let [item (:item @!state)
        now (js/Date.now)
        delta (- now (or (:started-at @!state) now))
        full-time-label (ms->time-str delta)]
    (set! (.-text item) (string/replace full-time-label
                                        #"^(00:){1,2}"
                                        ""))
    (swap! !state assoc :full-time-label full-time-label)))

(defn set-item-command! [item command]
  (set! (.-command item)
        (clj->js
         {:command "joyride.runCode"
          :arguments [(-> commands command :command)]}))
  (let [started-at (js/Date. (:started-at @!state))
        started-at-tooltip (string/replace (.toISOString started-at)
                                           #"(T|\.\d{3}Z$)"
                                           " ")]
    (set! (.-tooltip item) (str "Started at: "
                                started-at-tooltip
                                "(" (-> commands command :hint) ")"))))

(defn stop! []
  (let [timer (:timer @!state)]
    (swap! !state dissoc :timer)
    (swap! !state update :timers pop)
    (set-item-command! (:item @!state) :restart)
    (js/clearInterval timer)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn start! []
  (let [timer (peek (:timers
                     (swap! !state update :timers conj
                            (js/setInterval (fn [] (#'update-item!)) 50))))]
    (swap! !state assoc :timer timer :started-at (js/Date.now))
    (set-item-command! (:item @!state) :stop)))

(defn remove-item! []
  (when-let [item (:item @!state)]
    (.dispose item)
    (swap! !state dissoc :item :started-at))
  (when (count (:items @!state))
    (swap! !state update :items pop)))

(defn init! []
  (let [item (peek (:items
                    (swap! !state update :items conj
                           (vscode/window.createStatusBarItem
                            vscode/StatusBarAlignment.Left
                            -1000))))]
    (swap! !state assoc :item item)
    (set-item-command! (:item @!state) :start)
    (update-item!)
    (.show item)))

(when (= (joy/invoked-script) joy/*file*)
  (if (:item @!state)
    (if (:timer @!state)
      (.appendLine (joy/output-channel)
                   "showtime: You need to stop the timer to remove the status bar item.")
      (remove-item!))
    (init!)))

(comment
  (update-item!)
  (remove-item!)
  (stop!)
  @!state
  ()
  :rcf)


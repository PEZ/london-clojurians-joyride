# The Joyride API

<div class="slide scroll-y">
<div>

## Libraries

* `joyride.core` (The extension's context and Output channel)
* The Clojure core library, including, `clojure.set`, `clojure.edn`, `clojure.string`, `clojure.walk`, `clojure.data`, `cljs.test`, and `clojure.zip`
* The Promesa library
* `vscode` + extension modules
* NPM access
</div>
<div>

## Commands

* `joyride.runCode`
* `joyride.evaluateSelection`
* `joyride.runUserScript`
* `joyride.runWorkspaceScript`
<div>

``` clojure
(-> (vscode/window.showInputBox) (.then identity))
```

## The Joyride classpath

* User: `~/.config/joyride/scripts`
* Workspace:<br> `<ws-root>/.joyride/scripts`
</div>
</div>
</div>

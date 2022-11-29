# The Joyride API

<div class="slide scroll-y">
<div>

## Libraries

* `"vscode"`
* `"ext://extension-id"`
* `joyride.core` (The extension's context and Output channel)
* The Clojure core library, including, `clojure.set`, `clojure.edn`, `clojure.string`, `clojure.walk`, `clojure.data`, `cljs.test`, and `clojure.zip`
* `promesa.core` (A truly sweet Promise interface)
* `"foo"` (NPM modules)
</div>
<div>

## The Joyride classpath

* User: `~/.config/joyride/scripts`
* Workspace:<br> `<ws-root>/.joyride/scripts`

## Commands

* `joyride.runCode`
* `joyride.evaluateSelection`
* `joyride.runUserScript`
* `joyride.runWorkspaceScript`
<div>

``` clojure
(-> (vscode/window.showInputBox) (.then identity))
```
</div>
</div>
</div>

# Joyride @ London Clojurians, Nov 29 2022

The meetup was about how to hack [Visual Studio Code](https://code.visualstudio.com/) like it was [Emacs](https://www.gnu.org/software/emacs/), using the VS Code Extension [Joyride](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.joyride).

![image](https://user-images.githubusercontent.com/30010/204566090-b87659b0-942f-4809-b101-7e827d8c56ba.png)

* Meetup: https://www.meetup.com/london-clojurians/events/286030325/
* Joyride repo: https://github.com/BetterThanTomorrow/joyride

## Examples included

See the Workspace Scripts, [.joyride/scripts](.joyride/scripts), folder.

You find the example with the pulsating status bar item is in this file:
* [.joyride/scripts/london_examples.cljs](.joyride/scripts/london_examples.cljs)

## The [SCI](https://github.com/babashka/sci) slides

@borkdude still seems to prefer Keynote over VS Code. 😂

* [joyride-sci.pdf](joyride-sci.pdf)

## How to use the `next_slide.cljs` script

To run this slideshow:

0. In VS Code, Search for **Joy** in the Extensions pane, and install **Joyride**.
1. Open this project
1. Define keyboard shortcuts
    ```jsonc
    { // Activate next-slide
        "key": "ctrl+alt+j n",
        "command": "joyride.runCode",
        "args": "(next-slide/toggle-active!)"
    },
    { // Forward slide when not inserting text
        "key": "right",
        "command": "joyride.runCode",
        "args": "(next-slide/next! true)",
        "when": "next-slide:active && !inputFocus"
    },
    { // Backward slide when not inserting text
        "key": "left",
        "command": "joyride.runCode",
        "args": "(next-slide/next! false)",
        "when": "next-slide:active && !inputFocus"
    },
    { // Forward slide unconditionally
        "key": "ctrl+alt+j right",
        "command": "joyride.runCode",
        "args": "(next-slide/next! true)",
    },
    { // Backward slide unconditionally
        "key": "ctrl+alt+j left",
        "command": "joyride.runCode",
        "args": "(next-slide/next! false)"
    },
    ```
1. Command palette: **Joyride: Run Workspace Script**
   * Select `next_slide.cljs`
1. Activate **next-slide**: <kbd>ctrl</kbd>+<kbd>alt</kbd>+<kbd>j</kbd> <kbd>n</kbd>
1. Press <kbd>ctrl</kbd>+<kbd>alt</kbd>+<kbd>j</kbd> <kbd>left</kbd>
   * This should open the first slide, `lobby.md`.
1. Use <kbd>right</kbd> and <kbd>left</kbd> to run through the slides.
1. Use the command: **View: Toggle Zen Mode**

## How to use `next_slide.cljs` for your presentations

The important files here are:

* [.joyride/scripts/next_slide.cljs](.joyride/scripts/next_slide.cljs), consider placing this in your user scripts folder (`<HOME>/.config/joyride/scripts/`)
* [slides.edn](slides.edn), sets up a vector of Markdown files, the slide deck
* [.vscode/settings.json](.vscode/settings.json), sets up zoom level, font sizes, and other ”presentation mode” things. (`zenMode.hideStatusBar` is set to `false` in this project, you might want to hide the status bar in Zen mode.)
* [slides/style.css](slides/style.css), the styling used for this presentation

Please let us know if you find use for this. Like in the **#joyride** channel over at [Clojurians Slack](http://clojurians.net).

## The [Clojuredocs](https://clojuredocs.org/) example

Instructions in the script itself:
* [.joyride/scripts/clojuredocs.cljs](.joyride/scripts/clojuredocs.cljs)

## How to use the Showtime (timer) script

0. Assuming Joyride is installed.

Copy [.joyride/scripts/showtime.cljs](.joyride/scripts/showtime.cljs) to your Joyride User Scripts folder

To add the timer to the statusbar:
1. **Joyride: Run User Script**
2. Select `showtime.cljs`

To start the timer: click it

To stop the timer: click it

To restart the timer: click it

To remove the timer from the statusbar:
0. Ensure the timer is stopped
1. **Joyride: Run User Script**
2. Select `showtime.cljs`

## The Terminal Example

There is a video on Youtube: [How to Control the VS Code Terminal with Joyride](https://www.youtube.com/watch?v=BqoIp1YuOic)

## Happy Joyriding! 🚗💨

Please join the `#joyride` channel on [Clojurians Slack](http://clojurians.net) to get help, provide feedback, and share your solutions. You can also reach us in the [Discussions section](https://github.com/BetterThanTomorrow/joyride/discussions) of the Joyride repository.
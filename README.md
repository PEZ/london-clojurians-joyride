# Joyride @ London Clojurians, Nov 29 2022

![image](https://user-images.githubusercontent.com/30010/204566090-b87659b0-942f-4809-b101-7e827d8c56ba.png)

* Meetup: https://www.meetup.com/london-clojurians/events/286030325/
* Joyride repo: https://github.com/BetterThanTomorrow/joyride

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


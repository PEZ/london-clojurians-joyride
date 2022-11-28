Remember:

- Disable Double-click edits
- Ensure statusbar is showing
- Clean up statusbar items
- Clean slate for Keyboard shortcuts
- Enable showtime
- Enable Screencast mode
- Enable next-slide
- Enable Zen-mode
  - Ensure Zen-mode shows notifications
- Show lobby slide

# Joyride VS Code

Hi there!

I'm Peter, one of the creators of Joyride.
(Michiel)

We have some questions for you. Please use the chat to answer them.

What's your main editor? Write in the chat. Any VS Code users that do not use Calva?

How many here have developed an extension/plugin/mode for a code editor? Please mention extension and editor in the chat.

Do you configure your editor a lot?

How many of you have scripted your editor?

(Next slide)

## Agenda

Tonight we are going to talk about
* **Why** we created Joyride,
    * and what it is, 
    * and what you can do with it
* I'm going to show some examples
* Michiel is going to tell us a bit about what makes Joyride even possible
* Then we'll look at some more examples
* Though, we really want to hear your questions, so we'll do a Q&A before that
* You can continue asking questions when we show some more examples
* And then maybe this all turns into a mob programming session where we hack VS Code in user space together
* If you have some example you'd like to show, that would be awesome

Please feel free to raise your hand during the talk to get clarifications if something is unclear. But if it's more of a curiosity thing, please write it in the chat. Bruno can help us find it there during the Q&A.

(Next slide, remember where the mouse cursor is.)

## Why Joyride?

Joyride is an extension for Visual Studio Code.

VS Code is the most successful of the modern editors in terms of uptake. It has about 24 million users per month on average, and it is growing. 

There are 41,600 extensions on the Marketplace. 

Among Clojurians, VS Code has about a 20% share of the users, according to the annual Clojure Survey.

### There's an Extension for that!

It's worth reasoning around why there are so many extensions!

One reason is the VS Code Extension API. It is carefully designed, well documented, and easy to use.

By design, it is far less permitting than Emacs or Atom's extensibility models. This makes for a quite consisten user experience for VS Code users, without the need for a review process.

No review process means that you can create, and make an extension available, within 5 minutes, even less. Updates are processed within minutes too.

(Hover Theseus) This stricter API is of course also limiting. We extension developers often have to just skip some of our plans, because the API refuses to allow us to realize them.

This is especially true for extensions supporting interactive programming, something Calva as well as extensions like the one for Julia, have been, and continues to be, limited by.

(Unhover) This is a hard balance for the VS Code team to strike. I think they are doing good. From me they get a 10/10, would try again, rating for the balance. I think many Extension developers feel like this.

The VS Code Extension community is very friendly and helpful. I think Eric Amodio, creator of the hugely successful GitLens, has helped everyone of us. Many times. In this way he has contributed greatly into shaping the mode of cooperation between extension developers and also with the VS Code team, which Eric has been part of.

To the community I count Isidor Nikolic, a VS Code product manager, who takes full part and makes sure our questions and feedback reach the people it concerns. 

41,600 extensions is impressive!

(Hover However) Even if it is not 41,600 high quality extensions. There is often many extensions covering the same use case. Which is not necessarily a bad thing. One user's idea about what a good solution looks like, can be quite different from some other user. For big use cases I'd say that many solutions is a good thing. Competition is good.

For small use cases it's a different story. Often, each extension does not add enough of competition to warrant the cognitive load it brings with it. Which one of all these word counters should I use? From the **descriptions** it can be quite hard to tell.

I've spent all too much time searching for and testing extensions. If VS Code would let me hack a script that counts words my way, that could actually take less time, and I will end up with something tailored to me.

What I am saying is that this lack of user space scriptability contributes as a success factor for VS Code Extension growth.

I really think it is fantastic with this ”There's an extension for that” situation. ”There are 20 extensions for that” is sometimes frustrating and user space hackability can help here.  

VS Code is very configurable, via settings. You can also call any of its commands, including those provided by extensions, from keyboard shortcuts you define. Keyboard shortcuts can also provide arguments.

Though, used like this they truly are commands. Or statements. There is no way to deal with the return values of the commands. 

This closes the door to even the simplest definition of a script. If this scheme is turing complete in some way, it would blow my mind.

To extend VS Code with code, you have to write an extension. And find ways to distribute it, if the Marketplace doesn't fit your use case.

This is where Joyride comes in.

(Next slide) This is the Why Joyride.

Joyride enables VS Code to be scriptable in user space. One way to use it is to pop open a prompt and enter whatever code you want there.

(Use command pallette "joycode".) This is the Joyride prompt for running some code. The code will have full access to the VS Code extension API. (ctrl+alt+v 1)

This particular code will actually put up yet another input prompt. Then a message box using the input. This message box will have buttons. And then another message box will show which button you used. It's a bit hard to follow all this on this tiny prompt, but take my word for it, or believe your own eyes in a second. We will look closer at code like this later.

We could do this because Joyride itself is an extension and offers the user its own access to the VS Code API. The API includes, but is not limited to, access to all VS Code commands, and all commands provided by installed extensions.

Unlike with keyboard shortcuts, now you can use the return values of these commands.

Joyride lets you do (almost) anything that an extension can do.
* Populate the status bar with information and buttons
* Provide a webview where any interactivity can be supported
* Provide menus
* Open some file editor and populate it with content
* Create a slide manager for your presentations
* Recreate some retro game
* Automate some UI tweaks that lack settings in VS Code
* And so on, and so forth.

The only thing Joyride can't provide is access to its manifest, where command palette entries, and settings schemas, webviews and some more of the full extension API resides. 

This manifest is static and can't be updated in runtime. That's horrible, but let's not go all grumpy about this limitation now. There are ways we can make Joyride to compensate for some of this. And as I tried to relay just before, there is plenty here that we can celebrate!

(Next slide)

Let's have a quick look at the Joyride API.

(Show output panel cmd+shift+u)
(Copy code and open run code, ctrl+alt+j c)

About `joyride.evaluateSelection` it does what it says it does. So we don't need to copy the code.

(Command palette "joysel", ctrl+option+J ENTER)

About User and Workspace Scripts. Joyride has these directories in its classpath. And we can get a menu of the scripts there and run them. Or open them.

Since Joyride has access to all extension commands, it also has access to its own commands. Which can be very useful. As we will see. But first let's take a look at a simple example.

Here we use vscode API to show a message box with a button again. And a vscode command to open the integrated simple browser.

Here we use vscode API to figure out the workspace root and the Uri to a file in there. Then we open that file. Which doesn't show it, actually. So we then show it.

Don't worry about this promise chain here. Joyride packs Promesa, so we can write more async/await-like code.

(london_examples.cljs open)

There is one more thing that Joyride brings to the table that I haven't mentioned yet. Something big. We have been using it a few times. Anyone have a guess? Guess in the chat, please.

Yes, a REPL, a real REPL. Joyride brings **Interactive Programming** to VS Code users, in **user space**.

When we run Joyride code we are using this REPL. We can modify VS Code and our scripts and functions, etcetera, with this REPL.

Though it is a bit clunky to use it this way. Therefore Joyride has an nREPL server, letting us connect and use an nREPL client. Like Calva. Which I have installed. Calva even has a command to start this server, and connect. Which in turn is because Joyride has API for starting its nREPL server.

(REPL command palette "startjoy".)

With this REPL you can ask VS Code things.

And we can continue to tell VS Code to behave in new ways. For instance: You can toggle the line numbers from the active text editor. The built in **settings** only allow to toggle this for all editors.

Oh, someone else has connected to my Joyride REPL. From the Netherlands. (Because I allow it, Joyride defaults to not allow this.) Which nREPL client are **you** using, Michiel? CIDER?

### Activation scripts (init.el-ish)

#### user_activate.cljs

#### workspace_activate.cljs

#### next_slide.cljs

This is a script for managing a simple slide deck, in the form of an EDN file that points at some Markdown files. The script has a state atom which keeps track of the current slide and some command-functions for moving back and forth in the slide deck.

Yes, it is what I am right now using for the ”slides” of this presentation.

Alexa, previous slide. Alexa, next slide. Alexa, wave to the audience.

I'm pretty sure you could let a real Alexa device have fun with your Joyride REPL, but in this house Alexa is not allowed. Call me Mr Tin Foil Hat.

## Joyride API

## Calva API

Use the Joyride REPL to evaluate things in the REPL of the program you are developing. Now you can take care of the results as well.

Example: Clojuredocs

Example: Ignore current form.

Example: Give yourself some Joyride REPL commands, beating Calva's limitation of one REPL connection per window.

Calva's API is in its infancy.

## npm

parse-html


## Scrap area


If you are a Calva user, you might wonder about Calva custom REPL commands? They let you run arbitrary code in the running program, right? But, unless you are hacking on Calva the running program is not the editor. When it comes to the editor, these commands suffer from the same limitation as keyboard shortcuts, in not giving the user a way to act on the results programmatically.

Custom REPL commands do offer a way to populate the code hover displays, which is very nice, and very useful, but not even close to be labeled as **scripting the editor in user space**.
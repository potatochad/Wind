## Wind App

Quick heads up, the code has a lot of renames. Examples:

* `m` -> mutable
* `mState` -> mutableState
* `r` -> remember
* `s` -> save / size
* `w` -> width
* `h` -> height
* `class Bar` -> (does nothing)
* ...

## Structure

The code has a weird structure too. The most important parts are:

`/Data`
`/Screens`
`/Imports`

### /Data

Most of the savable data sits here (including data classes and so on).

`OnStart`, `OnResume`, `OnXScreen` are also here because they felt more data-related.

### /Screens

This is all UI + other code that the user can SEE.

Most of the default UI functions it uses sit in `Imports/UI_visible`, or inside its own `/props` file/folder.

### /Imports

Imagine it as all my tools: hammer, nails, everything I use everywhere and often. Basically, dependencies I wish existed.

`/Imports/Utils` -> all functions that the USER cannot see (with a few exceptions, like `Vlog` and similar).

`/Imports/UI_visible` -> tools the user can see. They are treated as default UI elements (`Icons`, `Buttons`, `Screens`).

---

If you have any legal or other concerns, including copyright, trademark, use of the name, privacy policy, or similar matters, please contact:

**[productivity.shield@gmail.com](mailto:productivity.shield@gmail.com)**

---

Also, if you're still reading this, I am surprised and grateful.

If you're interested: I am 17, have been programming Kotlin for almost 2 years, and for now it is my favourite language.

I haven't really watched a YouTube video on how to program and mostly free-styled it.

If you like my work, please contribute, share it, and I wish you a dope day.

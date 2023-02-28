# Reasoned Schemer with Clojure and Clerk

Exercsies from [The Reasoned Schemer][1] using Clojure's [core.logic][2] and the [Clerk][3] notebook library.

## How to run

Run the following command (or use [just][4] to run the provided [justfile](justfile)):

```
$ clj -M -m reasoned
```

This will launch a browser.

When you modify a file in the [notebooks](notebooks) directory, the file will
load in the browser.

You can use the `touch` command to trigger clerk to loading a notebook in the browser.

```
$ touch notebooks/ch1.clj
```

[1]: https://mitpress.mit.edu/9780262535519/the-reasoned-schemer/
[2]: https://github.com/clojure/core.logic
[3]: https://book.clerk.vision/
[4]: https://just.systems/man/en/

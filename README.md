# Game Spinner

**Unit 2A — Classes, Methods & Strings** · Pairs with lecture 2.2 Methods & Keywords (Day 1–2); uses the `Math.random()` range formula from 2.4

A board game needs a spinner: a disc split into equal sectors, numbered
1 through *n*. Spin it and the arrow lands on one of them. The game also
cares about *runs* — how many times in a row the arrow has landed on the
same number, because a long run earns a bonus. You are writing the two
methods that make a `GameSpinner` object work: `spin()` and `currentRun()`.

This is a small class with three private fields and two methods that
read and update them. That is the whole point of the assignment: a method
is not just a calculation, it is something that can **change the state of
an object** and **report on it later**. This problem is written in the
style of an AP Computer Science A free-response question — the table
below is exactly the kind of table the exam gives you.

---

## What you are given

| File | Status | Purpose |
|---|---|---|
| `src/main/java/GameSpinner.java` | **you complete this** | the spinner class: fields and constructor are written, you write `spin()` and `currentRun()` |
| `src/test/java/SpinTest.java` | provided | the autograder's tests for `spin()` — read them |
| `src/test/java/CurrentRunTest.java` | provided | the autograder's tests for `currentRun()` — read them |
| `pom.xml`, `grading.json`, `.gitignore` | provided | build and grading setup — do not edit |

## The class

```
GameSpinner
  private int sectors           how many sectors; spin() returns 1..sectors
  private int previousSpin = 0  the most recent spin result (0 = never spun)
  private int currentLength = 0 the length of the current run (0 = never spun)
```

The constructor `GameSpinner(int s)` is provided and just stores `s` in
`sectors`. Do not add fields; the three you have are enough.

## What to write

| Method | Points | What it does |
|---|---|---|
| `int spin()` | 40 | Picks a random integer from 1 to `sectors` inclusive, updates the run bookkeeping, and **returns the value spun**. |
| `int currentRun()` | 60 | Returns the length of the current run: how many spins in a row (counting the most recent) landed on the most recent value. 0 if the spinner has never been spun. |

### `spin()`

1. Pick the value: `int result = (int) (Math.random() * sectors) + 1;`
   `Math.random()` gives a double in `[0, 1)`. Multiplying by `sectors`
   and casting gives an int in `0..sectors-1`; the `+ 1` shifts it to
   `1..sectors`. **Forget the `+ 1` and you will spin a 0** — the tests
   check for exactly that.
2. Compare `result` to `previousSpin`. Same value → the run continues:
   `currentLength++`. Different value → a new run starts: `currentLength = 1`.
   **A new run has length 1, not 0** — the spin that broke the old run is
   the first spin of the new one.
3. Remember this spin: `previousSpin = result;`. If you skip this step the
   run can never get longer than 1.
4. `return result;`

The order matters: compare *before* you overwrite `previousSpin`.

### `currentRun()`

Return `currentLength`. That is all. It must **not** spin — calling
`currentRun()` three times in a row must give the same answer three times.

## Examples

The table from the original problem. `spin()` is random, so the values in
the middle column are *one possible* outcome; what is fixed is how
`currentRun()` must respond to them.

| Statements | Value returned (blank if no value returned) | Comment |
|---|---|---|
| `GameSpinner g = new GameSpinner(4);` | | Creates a new spinner with four sectors |
| `g.currentRun();` | `0` | Returns the length of the current run. The length of the current run is initially `0` because no spins have occurred. |
| `g.spin();` | `3` | Returns a random integer between `1` and `4`, inclusive. In this case, `3` is returned. |
| `g.currentRun();` | `1` | The length of the current run is `1` because there has been one spin of `3` so far. |
| `g.spin();` | `3` | Returns a random integer between `1` and `4`, inclusive. In this case, `3` is returned. |
| `g.currentRun();` | `2` | The length of the current run is `2` because there have been two `3`s in a row. |
| `g.spin();` | `4` | Returns a random integer between `1` and `4`, inclusive. In this case, `4` is returned. |
| `g.currentRun();` | `1` | The length of the current run is `1` because the spin of `4` is different from the value of the spin in the previous run of two `3`s. |
| `g.spin();` | `3` | Returns a random integer between `1` and `4`, inclusive. In this case, `3` is returned. |
| `g.currentRun();` | `1` | The length of the current run is `1` because the spin of `3` is different from the value of the spin in the previous run of one `4`. |
| `g.spin();` | `1` | Returns a random integer between `1` and `4`, inclusive. In this case, `1` is returned. |
| `g.spin();` | `1` | Returns a random integer between `1` and `4`, inclusive. In this case, `1` is returned. |
| `g.spin();` | `1` | Returns a random integer between `1` and `4`, inclusive. In this case, `1` is returned. |
| `g.currentRun();` | `3` | The length of the current run is `3` because there have been three consecutive `1`s since the previous run of one `3`. |

Two more you can trace by hand:

- `new GameSpinner(1)` — the only possible spin is `1`, so every spin
  extends the run. After 7 spins, `currentRun()` is `7`. The tests lean on
  this because it makes a random method predictable.
- `new GameSpinner(2)` — spins of `2, 2, 1, 1, 1, 2` give `currentRun()`
  values of `1, 2, 1, 2, 3, 1` after each spin.

## Running the tests

`mvn test` runs everything; `mvn test -Dtest=<ClassName>` runs one rubric line.

| Test class | Rubric line | Points |
|---|---|---|
| `SpinTest` | spin returns a random value in 1..sectors | 40 |
| `CurrentRunTest` | currentRun tracks the length of the current run | 60 |

The autograder awards a rubric line only when **every** test in that class
passes. Most of `CurrentRunTest` is really testing the bookkeeping inside
`spin()` — `currentRun()` itself is one line, but it can only be right if
`spin()` keeps `previousSpin` and `currentLength` up to date.

## Suggested order

1. Write `currentRun()` first — `return currentLength;`. It cannot be tested
   until `spin()` works, but it takes ten seconds and you will not forget it.
2. In `spin()`, compute the random value and return it. Run `SpinTest`.
   If you see a `0` in the failure message, you forgot the `+ 1`.
3. Add the `if / else` on `previousSpin`, then `previousSpin = result;`.
   Run `CurrentRunTest`. If the run never gets past 1, you are not saving
   `previousSpin`. If it drops to 0 after a change, you are resetting to 0
   instead of 1.
4. Write a short `main` in a scratch class (or use jshell) that spins a
   4-sector spinner ten times and prints the value and `currentRun()` after
   each spin. Check it against the table above.

## Rules of the road

- AP Java subset only: `int`, `Math.random()`, `if`/`else`, the three
  fields. No `java.util.Random`, no arrays, no ArrayList — you do not need
  them.
- Do not change the method headers, the field declarations, or the
  constructor. Do not add fields.
- `spin()` must be the only method that changes state. `currentRun()` is an
  accessor.
- Do not touch `src/test`, `pom.xml`, `grading.json`, or `.github`. The
  autograder checks that they are byte-identical to the template before it
  runs a single test; if they differ it stops and awards nothing, and the
  change shows up in the roster.

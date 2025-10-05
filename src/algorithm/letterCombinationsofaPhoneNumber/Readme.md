# LeetCode Problems — Java (OOP, IntelliJ-ready)

A curated collection of **LeetCode** solutions in **Java**, designed with clean **OOP**, readable code, and friction-free execution in **IntelliJ IDEA** or from the command line.

> **Showcase:** _17. Letter Combinations of a Phone Number_ — implemented with a pluggable digit→letters mapper and a backtracking solver.

---

## 🔖 Badges
![Java](https://img.shields.io/badge/Java-17%2B-red)
![Build](https://img.shields.io/badge/build-IntelliJ-green)
![Style](https://img.shields.io/badge/style-OOP-blue)

---

## 📦 Project Structure

.
├── src
│ └── algorithm
│ ├── letterCombinationsofaPhoneNumber/
│ │ ├── BacktrackingLetterCombinations.java
│ │ ├── ClassicPhoneMapper.java
│ │ ├── DigitToLetters.java
│ │ ├── LetterCombinationsSolver.java
│ │ └── Main.java
├── .gitignore
└── LeetCodeProblems.iml

> **Package naming:** Files inside `letterCombinationsofaPhoneNumber/` use  
> `package algorithm.letterCombinationsofaPhoneNumber;`  
> If your IDE shows a different package (e.g., `leetcode.phone`), update the `package` line to match the folder path.

---

## 🧩 Problem 17 — Letter Combinations of a Phone Number

### OOP Design

- **`DigitToLetters`** — interface for digit→letters mapping.
- **`ClassicPhoneMapper`** — standard keypad mapping for digits `2..9`.
- **`LetterCombinationsSolver`** — interface for solvers.
- **`BacktrackingLetterCombinations`** — DFS + **backtracking** implementation.
- **`Main`** — CLI entry point (interactive mode, single-run mode, and a built-in mini test harness).

**Why this design?** You can swap the mapper (e.g., locale-specific keypad) or add another strategy (e.g., BFS) without changing client code.

### Algorithmic Notes

- DFS over the digit string; at each depth choose one letter for the current digit.
- **Time complexity:** `O(k^n)` with `k ∈ {3,4}` ⇒ typically between `O(3^n)` and `O(4^n)` where `n` is the number of digits.
- **Space complexity:** `O(n)` for the recursion path (plus the size of the output).

---

## ▶️ How to Run

### Option A — IntelliJ IDEA

1. Open the project in IntelliJ.
2. Right-click `src/` → **Mark Directory as → Sources Root**.
3. Ensure each file’s `package` line matches its folder path (see above).
4. Right-click `Main.java` → **Run 'Main.main()'**.

**Run modes (inside `Main`):**
- **Interactive (default):** run with **no arguments** → prompts for digits `[2-9]` or runs demo when empty.
- **Single input:** add a program argument, e.g. `23`.
- **Tests:** run with `--test` to execute the built-in tests.

### Option B — Command Line

> From the project root:

**Unix/macOS (bash/zsh):**
```bash
# Compile all sources into ./out
javac -d out $(find src -name "*.java")

# Run (interactive)
java -cp out algorithm.letterCombinationsofaPhoneNumber.Main

# Run with input digits
java -cp out algorithm.letterCombinationsofaPhoneNumber.Main 23

# Run internal tests
java -cp out algorithm.letterCombinationsofaPhoneNumber.Main --test

Windows (PowerShell):
# Compile
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | javac -d out @()

# Run (interactive)
java -cp out algorithm.letterCombinationsofaPhoneNumber.Main

# Run with input digits
java -cp out algorithm.letterCombinationsofaPhoneNumber.Main 23

# Run internal tests
java -cp out algorithm.letterCombinationsofaPhoneNumber.Main --test
```
🧪 Built-in Tests (No JUnit Needed)

The Main class includes a lightweight test harness:

Compares sets of combinations (order doesn’t matter).

Covers edge cases (empty input) and invalid input (digits outside 2..9).

Run:

java -cp out algorithm.letterCombinationsofaPhoneNumber.Main --test


Sample result:

Running tests...

✅ PASS  TC1: digits=23
✅ PASS  TC2: digits=2
✅ PASS  TC3: digits="" (empty)
✅ PASS  TC4: invalid contains 1
✅ PASS  TC5: digits=79 (4x4=16 combos)

RESULT: passed 5 / 5 tests

---
📘 Code Walkthrough (Quick)
---
DigitToLetters: lettersOf(char) + isValidDigits(String).

ClassicPhoneMapper: provides the mapping for 2..9.

BacktrackingLetterCombinations#solve:

Validate input digits.

Call dfs(digits, idx, path, res):

If idx == digits.length() → add path.toString() to results.

Else iterate over letters for digits[idx], append → recurse → backtrack (remove last char).

🧱 Adding New Problems

Create a new folder under src/algorithm/<problemName>/.

Add:

Interfaces to abstract key roles (e.g., parser, solver).

One or more implementations.

A Main with CLI usage (args + interactive) and a few tests.

Document time/space complexity and non-trivial edge cases near the code.

🛠️ Development Notes

Java: 17+ recommended (works with 11+).

Style: small, single-purpose classes; clear naming; immutability when practical.

Error handling: invalid inputs → IllegalArgumentException with a clear message.

Performance: use StringBuilder when building strings in loops/recursion.

🖨️ Example Output
> digits = "23"
Combinations (9): [ad, ae, af, bd, be, bf, cd, ce, cf]
Elapsed: 1 ms

🤝 Contributing

PRs are welcome. For a new problem:

follow the folder + OOP structure,

include a Main with interactive usage and small tests,

document complexities and tricky cases.
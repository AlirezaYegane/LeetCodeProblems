# RegexMatch

A clean OOP solution for LeetCode #10 (Regular Expression Matching) that supports only `.` and `*` and must match the **entire** string.

## Design
- `IMatcher`: interface (single method `isMatch`)
- `DPRegexMatcher`: dynamic-programming implementation (time `O(m*n)`, space `O(m*n)`)
- `Main`: CLI with examples and interactive mode

## How it works (kid-simple)
We build a table `dp[i][j]`:
- `dp[i][j]` is **true** if the first `i` chars of `s` match the first `j` chars of `p`.
- Normal char or `.`: last chars must match **and** `dp[i-1][j-1]` must be true.
- `*`: either
    - use **zero** of the previous token → `dp[i][j-2]`, or
    - use **one-or-more** if last char matches previous token → `dp[i-1][j]`.

## Run
```bash
# compile
javac -d out src/main/java/algo/regex/*.java

# run samples
java -cp out algo.regex.Main --cases

# run single check
java -cp out algo.regex.Main
"aa" "a*"

# interactive
java -cp out algo.regex.Main
> s=ab
> p=.*
> :run

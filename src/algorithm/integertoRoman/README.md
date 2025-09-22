# Integer to Roman 
package: integertoRoman

This project converts an integer (1..3999) to a Roman numeral using a clean,
O(1) greedy algorithm implemented with a 13-pair lookup (including subtractive forms).

## Why this approach?
- **Simple & readable**: the entire rule-set is in one fixed table.
- **Truly O(1)** time and space for the problem constraints (1..3999).
- **Greedy correctness**: descending values + subtractive pairs guarantee a canonical result.

## Project structure
integertoRoman/
└─ src/
└─ integertoroman/
├─ RomanNumeralConverter.java
├─ GreedyRomanConverter.java
└─ Main.java

## Open in IntelliJ
1. In IntelliJ IDEA: *File* → *New* → *Project from Existing Sources...*
2. Select the `integertoRoman` directory.
3. Ensure the `src` folder is marked as *Sources Root* (right-click → *Mark Directory as*).
4. Set project SDK (Java 17+ recommended).

## Run
- **Demo mode** (no args): runs a set of sample numbers.

# Longest Common Prefix — Java

## ⬇️ Quick Copy
```bash
# Compile only the LCP module
javac -encoding UTF-8 -d out src/algorithm/longestCommonPrefix/*.java

# Run (MinMax strategy – default)
java -cp out algorithm.longestCommonPrefix.Main flower flow flight

# Run (Binary Search strategy)
java -cp out algorithm.longestCommonPrefix.Main --method=binary interstellar interstate

# Run tests / benchmark / help
java -cp out algorithm.longestCommonPrefix.Main --run-tests
java -cp out algorithm.longestCommonPrefix.Main --benchmark=50000 --method=minmax --randlen=24
java -cp out algorithm.longestCommonPrefix.Main --help
```


Overview

A clean, production-ready solution to Longest Common Prefix (LCP) in Java with solid OOP using the Strategy pattern.
Two algorithms are implemented:

MinMaxStrategy (default): fastest and simplest for typical LeetCode constraints.

BinarySearchStrategy: useful when strings are extremely long.

The project also ships with a comprehensive CLI, self-tests, and a lightweight benchmark.

src/
└─ algorithm/
└─ longestCommonPrefix/
├─ LCPStrategy.java
├─ MinMaxStrategy.java
├─ BinarySearchStrategy.java
├─ Solution.java
└─ Main.java

Package name used in the code: algorithm.longestCommonPrefix

Why Strategy (OOP)?

Open/Closed: add new LCP algorithms without touching existing code.

Single Responsibility: each algorithm in its own class.

Dependency Injection: Solution accepts any LCPStrategy (constructor/setter).

Testability: swap strategies in tests and benchmarks easily.

Algorithms & Complexity
Strategy	Time	Space	Notes
MinMaxStrategy	O(n · L)	O(1)	LCP of the array equals LCP of lexicographic min and max. Excellent for short/medium strings.
BinarySearchStrategy	O(n · log L)	O(1)	Binary search on prefix length with startsWith; better when strings are very long.

n = number of strings, L = length of the shortest string / LCP.

IntelliJ Run

Open the project in IntelliJ (JDK 17+ recommended).

Right-click Main → Run 'Main'.

Pass Program arguments (e.g. flower flow flight or --method=binary interstellar interstate).

CLI Usage
java -cp out algorithm.longestCommonPrefix.Main [options] [WORDS...]


Options

--method=minmax|binary choose algorithm (default: minmax)

--file=PATH read words from a file (space/comma separated per line)

--run-tests run built-in smoke & edge tests and exit

--benchmark=N generate N random words and time the algorithm

--randlen=L random word length for benchmark (default: 12)

-h, --help show help

Examples

java -cp out algorithm.longestCommonPrefix.Main flower flow flight
java -cp out algorithm.longestCommonPrefix.Main --method=binary interstellar interstate
java -cp out algorithm.longestCommonPrefix.Main --file=./words.txt
java -cp out algorithm.longestCommonPrefix.Main --run-tests
java -cp out algorithm.longestCommonPrefix.Main --benchmark=100000 --method=minmax --randlen=20

Input from File

Each line may contain one or more words separated by space or comma.

To include an empty string, use a blank token (e.g., two separators in a row) or an empty line.

Example words.txt

flower,flow,flight
dog racecar car

سلام سلامت سلامانه


Run:

java -cp out algorithm.longestCommonPrefix.Main --file=./words.txt

Built-in Tests

Run:

java -cp out algorithm.longestCommonPrefix.Main --run-tests


Covers: empty array, empty strings, identical strings, no common prefix, Unicode (Persian), case-sensitivity, and typical samples.
Both strategies are executed and compared.

Benchmark
java -cp out algorithm.longestCommonPrefix.Main --benchmark=50000 --method=minmax --randlen=24


Generates random data (with a small shared prefix in 70% of samples) and reports elapsed time.

LeetCode-Style Usage

If you only need the standard LeetCode signature:

class Solution {
public String longestCommonPrefix(String[] strs) {
// choose one strategy:
return new algorithm.longestCommonPrefix.MinMaxStrategy().compute(strs);
// or:
// return new algorithm.longestCommonPrefix.BinarySearchStrategy().compute(strs);
}
}

Notes

Strategies are null-tolerant (treat null strings as "" internally).

The CLI prints a demo when no input is provided.

You can easily add other strategies (e.g., Trie) by implementing LCPStrategy.
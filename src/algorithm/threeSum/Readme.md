# 3Sum — Java Implementation 

A clean, **object-oriented** Java solution for [LeetCode #15: 3Sum](https://leetcode.com/problems/3sum/).  
The project separates **interface**, **implementation**, **LeetCode adapter**, and a **professional runner** with examples, CLI, fuzz tests, and correctness checks.

---

## 🔧 Problem
Given an integer array `nums`, return all **unique** triplets `[nums[i], nums[j], nums[k]]` such that:

i != j, i != k, j != k
nums[i] + nums[j] + nums[k] == 0


No duplicate triplets are allowed in the output.

---

## 📂 Structure
src/
└── threeSum/
├── Main.java # Runner: examples, CLI, fuzz tests, validations, timing
├── Solution.java # LeetCode-style adapter (public List<List<Integer>> threeSum(int[]))
├── ThreeSumSolver.java # Interface defining the solver contract
└── TwoPointerThreeSumSolver.java # Sort + two-pointer implementation (O(n^2), O(1) extra space)


---

## 🧠 Algorithm (high level)

1. **Sort** the array (non-decreasing).
2. For each index `i`, run a **two-pointer** search on `(l=i+1, r=n-1)`:
    - If sum `< 0` → move `l++`; if sum `> 0` → move `r--`; if `== 0` → record triplet.
3. **Skip duplicates** for `i`, `l`, and `r` to avoid repeated triplets.
4. Early pruning: if `nums[i] > 0`, break; also bound checks using smallest/largest pairs.

**Complexity:** `O(n^2)` time (dominant), `O(1)` extra space (excluding output).

---

## ▶️ Run (IntelliJ)

1. Open the project in **IntelliJ IDEA (Java 8+)**.
2. Ensure files are under package `threeSum` (or adjust the `package` line accordingly).
3. Run `Main.main()`.
    - By default it prints outputs for curated examples and runs a quick fuzz test.

---

## 🖥️ CLI (Program Arguments in Run Configuration)

- **Help**
- **Run on a custom array**
  --array "-1,0,1,2,-1,-4"
- **Fuzz testing (randomized, with correctness checks)**
  --fuzz --iterations 200 --size 60 --min -10 --max 10 --seed 42
- **Skip default fuzzing**
  --no-fuzz

---

## ✅ Example (default run)
=== Curated Examples ===

Example 1
Input : [-1, 0, 1, 2, -1, -4]
Output: [[-1, -1, 2], [-1, 0, 1]]

Example 2
Input : [0, 1, 1]
Output: []

Example 3
Input : [0, 0, 0]
Output: [[0, 0, 0]]

Example 4 (extra mixed)
Input : [-2, 0, 0, 2, 2, -2, -1, 1]
Output: [[-2, 0, 2], [-1, 0, 1], ...]
=== Curated Examples ===

Example 1
Input : [-1, 0, 1, 2, -1, -4]
Output: [[-1, -1, 2], [-1, 0, 1]]

Example 2
Input : [0, 1, 1]
Output: []

Example 3
Input : [0, 0, 0]
Output: [[0, 0, 0]]

Example 4 (extra mixed)
Input : [-2, 0, 0, 2, 2, -2, -1, 1]
Output: [[-2, 0, 2], [-1, 0, 1], ...]

---

## 🧪 Validation in `Main`
The runner asserts that:
- Each triplet has **size 3**.
- Each triplet **sums to zero**.
- Triplets are **non-decreasing** internally.
- **No duplicates** exist overall.

If any invariant fails, an `AssertionError` is thrown (useful during development and future refactors).

---

## 🔌 Swap/Extend Implementations
Add new strategies by implementing `ThreeSumSolver`, then inject:

```java
Solution solution = new Solution(new MyCustomThreeSumSolver());
```
The rest of the pipeline (runner, checks, CLI) works unchanged.

---

✍️ Author Notes

Designed for clarity and auditability in interviews and production code reviews.

The two-pointer approach here is the standard optimal
𝑂
(
𝑛
2
)
O(n
2
) solution; 3 nested loops
𝑂
(
𝑛
3
)
O(n
3
) are intentionally avoided.
# 3Sum Closest — Java 

A clean, production-style solution for LeetCode **16. 3Sum Closest** implemented in Java with a proper OOP design (interface + implementation), a professional `Main` runner that supports **CLI usage**, **deterministic tests**, **randomized property tests**, and a light **benchmark**.

---

## Problem
Given an integer array `nums` (n ≥ 3) and a target integer `target`, find the **sum of three integers** in `nums` which is **closest** to `target`. Return that sum.

---

## Algorithm

**Sort + Two Pointers (+ pruning)**

1. Sort the array.
2. For each anchor index `i`, use two pointers `l` and `r` to find the closest sum.
3. Update the best sum when a closer one is found.
4. Light pruning:
    - `min3 = nums[i] + nums[i+1] + nums[i+2]`
    - `max3 = nums[i] + nums[n-1] + nums[n-2]`

**Complexity:**
- Time: **O(n²)**
- Space: **O(1)** (in-place sort)

---

## Project Structure

threeSumClosest/
├─ IThreeSumClosestSolver.java # Interface (contract)
├─ ThreeSumClosestSolver.java # Implementation (Sort + Two Pointers + Pruning)
└─ Main.java # Entry point: CLI + demo + tests + benchmark

yaml
Copy code

---

## How to Run

### IntelliJ
1. Create a Java project with package `threeSumClosest`.
2. Add the three files.
3. Run `Main`.

### CLI (Program arguments)
- Demo (default):  
  --mode=demo

python
Copy code
- Demo with custom input:  
  --mode=demo --nums="-1,2,1,-4" --target=1

diff
Copy code
- Interactive stdin:  
  --mode=stdin

diff
Copy code
- Deterministic tests:  
  --mode=check

arduino
Copy code
- Deterministic + random property tests:  
  --mode=check --random=1 --trials=300 --seed=42

diff
Copy code
- Benchmark:  
  --mode=bench --nStart=100 --nEnd=1000 --step=200 --trials=3 --seed=123

yaml
Copy code

---

## Examples

**Built-in Demo:**
nums=[-1, 2, 1, -4], target=1 -> closest=2
nums=[0, 0, 0], target=1 -> closest=0
nums=[1, 1, 1, 0], target=-100 -> closest=2

makefile
Copy code

**Interactive:**
nums: -1 2 1 -4
target: 1
Closest sum = 2

yaml
Copy code

---

## Testing

- **Deterministic suite**: classic and edge cases.
- **Randomized property tests**: cross-check against brute force.
- **Benchmark**: sanity check for O(n²) complexity growth.

---

## Notes

- Throws `IllegalArgumentException` if array has < 3 elements.
- Handles duplicates correctly.
- Uses `int` (safe under LeetCode constraints).
- On ties (two sums equally close), either is acceptable.

---


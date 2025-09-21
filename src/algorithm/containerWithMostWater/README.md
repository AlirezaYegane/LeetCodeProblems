# Container With Most Water — Java OOP Implementation

This project provides a clean, object-oriented Java solution for the classic **Container With Most Water** problem (LeetCode #11).  
The implementation uses the optimal **Two Pointers** approach with time complexity `O(n)` and space complexity `O(1)`.

---

## 📂 Project Structure

containerWithMostWater/
├── AreaStrategy.java # Strategy interface for different algorithms
├── ContainerService.java # Facade service wrapping the strategy
├── Main.java # CLI runner and interactive tester
└── TwoPointerStrategy.java # Optimal two-pointer implementation


---

## ⚙️ Algorithm

- Place two pointers: one at the beginning and one at the end of the height array.
- Compute area as:
area = min(height[left], height[right]) * (right - left)
- Move the pointer at the shorter line inward, because moving the taller one cannot increase the minimum height.
- Repeat until the pointers meet, keeping track of the maximum area.

This guarantees the maximum water container in linear time.

---

## ▶️ Running the Program

### 1. Run Sample Test Cases

java containerWithMostWater.Main --cases

### 2. Run with Custom Heights (CSV Input)
java containerWithMostWater.Main "1,8,6,2,5,4,8,3,7"
Output:
49


### 3. Interactive Mode
java containerWithMostWater.Main

Example session:
Container With Most Water — strategy: two-pointers
Type CSV heights (e.g., 1,8,6,2,5,4,8,3,7) or :cases or :q
> 1,8,6,2,5,4,8,3,7
max area = 49
> :cases
Example (LC):
heights=1,8,6,2,5,4,8,3,7
got=49  expected=49
> :q
>
### Sample Cases

Input: 1,8,6,2,5,4,8,3,7 → Output: 49

Input: 5,5,5,5,5 → Output: 20

Input: 0,0,0,0 → Output: 0

### Extensibility

The design follows the Strategy Pattern.

To add a new algorithm (e.g., brute force for learning purposes), simply implement AreaStrategy and plug it into ContainerService.

### References
https://chatgpt.com/g/g-p-68b9fc62f6fc819195455ab4bb1cb548-alireza-yegane/c/68d0065e-0378-832a-832b-ce19d557e8b0#:~:text=%F0%9F%93%96%20References-,LeetCode%20Problem%20%2311%20%E2%80%94%20Container%20With%20Most%20Water,-go
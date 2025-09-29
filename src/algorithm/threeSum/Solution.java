package algorithm.threeSum;

import java.util.List;

public class Solution {
    private final ThreeSumSolver solver;

    public Solution() {
        this.solver = new TwoPointerThreeSumSolver();
    }

    // dependency injection (for testing with other solvers)
    public Solution(ThreeSumSolver solver) {
        this.solver = solver;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        return solver.threeSum(nums);
    }
}
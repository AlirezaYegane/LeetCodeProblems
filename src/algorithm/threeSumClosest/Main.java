package algorithm.threeSumClosest;


import java.util.Arrays;

/**
 * Entry point for running 3Sum Closest problem tests.
 */
public class Main {
    private static void runCase(IThreeSumClosestSolver solver, int[] nums, int target, Integer expected) {
        int result = solver.threeSumClosest(nums.clone(), target);
        System.out.println("nums=" + Arrays.toString(nums) +
                ", target=" + target +
                " -> closestSum=" + result +
                (expected != null ? (" (expected≈ " + expected + ")") : ""));
    }

    public static void main(String[] args) {
        IThreeSumClosestSolver solver = new ThreeSumClosestSolver();

        // LeetCode Examples
        runCase(solver, new int[]{-1, 2, 1, -4}, 1, 2);
        runCase(solver, new int[]{0, 0, 0}, 1, 0);

        // Extra tests
        runCase(solver, new int[]{1, 1, 1, 0}, -100, 2);
        runCase(solver, new int[]{-3, -2, -5, 3, -4}, -1, -2);
        runCase(solver, new int[]{5, -7, 3, 2, 9, -1}, 4, null);
    }
}
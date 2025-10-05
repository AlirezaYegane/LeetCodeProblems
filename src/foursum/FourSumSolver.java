package foursum;

import java.util.List;

public interface FourSumSolver {
    /**
     * Returns all unique quadruplets whose sum equals target.
     * Time: O(n^3), Space: O(1) (excluding output).
     */
    List<List<Integer>> solve(int[] nums, int target);
}
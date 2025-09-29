package algorithm.threeSum;

import java.util.List;

public interface ThreeSumSolver {
    /**
     * Finds all unique triplets (i, j, k) such that nums[i] + nums[j] + nums[k] == 0.
     * @param nums input array (not null)
     * @return list of unique triplets; each triplet is a List<Integer> of size 3
     */
    List<List<Integer>> threeSum(int[] nums);
}
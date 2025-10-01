package algorithm.threeSumClosest;


import java.util.Arrays;

/**
 * Implementation of IThreeSumClosestSolver using
 * sorting + two-pointer + pruning approach.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 */
public class ThreeSumClosestSolver implements IThreeSumClosestSolver {

    @Override
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            throw new IllegalArgumentException("Array must contain at least 3 elements.");
        }

        Arrays.sort(nums);
        int n = nums.length;
        int best = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int min3 = nums[i] + nums[i + 1] + nums[i + 2];
            if (min3 > target) {
                if (Math.abs(min3 - target) < Math.abs(best - target)) best = min3;
                break;
            }

            int max3 = nums[i] + nums[n - 1] + nums[n - 2];
            if (max3 < target) {
                if (Math.abs(max3 - target) < Math.abs(best - target)) best = max3;
                continue;
            }

            int l = i + 1, r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];

                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }

                if (sum < target) {
                    l++;
                } else if (sum > target) {
                    r--;
                } else {
                    return target; // exact match
                }
            }
        }
        return best;
    }
}
package foursum;


import java.util.*;

/**
 * Optimized 4Sum: sort once + two outer loops + two-pointer + pruning + de-dup.
 */
public class TwoPointerFourSumSolver implements FourSumSolver {

    @Override
    public List<List<Integer>> solve(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 4) return ans;

        Arrays.sort(nums);
        final int n = nums.length;

        for (int i = 0; i <= n - 4; i++) {
            // skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // pruning on i
            long minWithI = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (minWithI > target) break; // further i will be larger
            long maxWithI = (long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3];
            if (maxWithI < target) continue;

            for (int j = i + 1; j <= n - 3; j++) {
                // skip duplicates for second element
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                // pruning on j
                long minWithIJ = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (minWithIJ > target) break;
                long maxWithIJ = (long) nums[i] + nums[j] + nums[n - 1] + nums[n - 2];
                if (maxWithIJ < target) continue;

                int l = j + 1, r = n - 1;
                while (l < r) {
                    long sum = (long) nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        ans.add(List.of(nums[i], nums[j], nums[l], nums[r]));
                        // advance and skip duplicates on l and r
                        l++;
                        while (l < r && nums[l] == nums[l - 1]) l++;
                        r--;
                        while (l < r && nums[r] == nums[r + 1]) r--;
                    } else if (sum < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }
        }
        return ans;
    }
}
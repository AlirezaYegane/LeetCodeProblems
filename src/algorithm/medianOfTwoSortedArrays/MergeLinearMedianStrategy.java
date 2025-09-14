package algorithm.medianOfTwoSortedArrays;

import java.util.Objects;

public final class MergeLinearMedianStrategy implements MedianStrategy {


    @Override
    public double findMedian(int[] nums1, int[] nums2) {
        Objects.requireNonNull(nums1, "nums1 is null");
        Objects.requireNonNull(nums2, "nums2 is null");
        ArrayValidator.ensureSorted(nums1);
        ArrayValidator.ensureSorted(nums2);

        int m = nums1.length, n = nums2.length;
        int total = m + n;
        int mid2 = total / 2;
        int mid1 = (total % 2 == 0) ? mid2 - 1 : mid2;

        int i = 0, j = 0, idx = -1;
        int prev = 0, curr = 0;

        while (i < m || j < n) {
            prev = curr;
            if (j == n || (i < m && nums1[i] <= nums2[j])) curr = nums1[i++];
            else curr = nums2[j++];
            idx++;

            if (idx == mid2) {
                if ((total & 1) == 1) return curr;
                return (prev + curr) / 2.0;
            }
        }
        return 0.0;
    }
}
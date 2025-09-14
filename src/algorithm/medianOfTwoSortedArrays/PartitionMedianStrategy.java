package algorithm.medianOfTwoSortedArrays;

import java.util.Objects;

public final class PartitionMedianStrategy implements MedianStrategy {
    @Override
    public double findMedian(int[] nums1, int[] nums2) {
        Objects.requireNonNull(nums1, "nums1 is null");
        Objects.requireNonNull(nums2, "nums2 is null");
        ArrayValidator.ensureSorted(nums1);
        ArrayValidator.ensureSorted(nums2);

        if (nums1.length == 0) {
            return medianSingleArray(nums2);
        }
        if (nums2.length == 0) {
            return medianSingleArray(nums1);
        }

        if (nums1.length > nums2.length) {
            return findMedian(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int total = m + n;
        int leftCount = (total + 1) / 2;

        int lo = 0, hi = m;
        while (lo <= hi) {
            int i = (lo + hi) >>> 1;
            int j = leftCount - i;

            int Aleft  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int Aright = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int Bleft  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int Bright = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (Aleft <= Bright && Bleft <= Aright) {
                if ((total & 1) == 1) {
                    return Math.max(Aleft, Bleft);
                } else {
                    int leftMax  = Math.max(Aleft, Bleft);
                    int rightMin = Math.min(Aright, Bright);
                    return ((double) leftMax + (double) rightMin) / 2.0;
                }
            } else if (Aleft > Bright) {
                hi = i - 1;
            } else {
                lo = i + 1;
            }
        }
        throw new IllegalArgumentException("Inputs must be sorted arrays.");
    }

    private double medianSingleArray(int[] arr) {
        int n = arr.length;
        if (n % 2 == 1) {
            return arr[n / 2];
        } else {
            return (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
        }
    }
}

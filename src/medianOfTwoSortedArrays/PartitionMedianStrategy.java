package medianOfTwoSortedArrays;

import java.util.Objects;

public class PartitionMedianStrategy implements MedianStrategy {
    @Override
    public double findMedian(int[] nums1, int[] nums2) {
        Objects.requireNonNull(nums1, "nums1 is null");
        Objects.requireNonNull(nums2, "nums2 is null");
        ArrayValidator.ensureSorted(nums1);
        ArrayValidator.ensureSorted(nums2);

        if (nums1.length>nums2.length) {
            return findMedian(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int total = (m+n);
        int leftCount = (total+1)/2;
        int low = 0;
        int high = m;


    }
}

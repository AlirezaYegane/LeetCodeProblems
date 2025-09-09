package medianOfTwoSortedArrays;

import java.util.Objects;

public final class MedianService {
    private final MedianStrategy strategy;

    public MedianService(MedianStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public double compute(int[] nums1, int[] nums2) {
        return strategy.findMedian(nums1, nums2);
    }
}
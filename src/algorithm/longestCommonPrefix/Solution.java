package algorithm.longestCommonPrefix;


/**
 * Facade/Context class using Strategy for LCP computation.
 * Default strategy is MinMaxStrategy (good trade-off for typical constraints).
 */
public class Solution {

    private LCPStrategy strategy;

    public Solution() {
        this.strategy = new MinMaxStrategy(); // default
    }

    public Solution(LCPStrategy strategy) {
        if (strategy == null) throw new IllegalArgumentException("strategy cannot be null");
        this.strategy = strategy;
    }

    public void setStrategy(LCPStrategy strategy) {
        if (strategy == null) throw new IllegalArgumentException("strategy cannot be null");
        this.strategy = strategy;
    }

    /**
     * LeetCode-style API
     */
    public String longestCommonPrefix(String[] strs) {
        return strategy.compute(strs);
    }

    // Optional explicit APIs (handy for tests)
    public String longestCommonPrefixMinMax(String[] strs) {
        return new MinMaxStrategy().compute(strs);
    }

    public String longestCommonPrefixBinarySearch(String[] strs) {
        return new BinarySearchStrategy().compute(strs);
    }
}
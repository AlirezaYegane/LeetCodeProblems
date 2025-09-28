package algorithm.longestCommonPrefix;


/**
 * Strategy interface for Longest Common Prefix algorithms.
 */
public interface LCPStrategy {
    /**
     * Computes the longest common prefix of the given strings.
     * @param strs input strings (may contain empty strings)
     * @return the LCP (never null)
     */
    String compute(String[] strs);
}
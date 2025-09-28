package algorithm.longestCommonPrefix;


/**
 * Binary search on prefix length.
 * Time: O(n·log L), Space: O(1)
 */
public class BinarySearchStrategy implements LCPStrategy {

    @Override
    public String compute(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        int minLen = Integer.MAX_VALUE;
        for (String s : strs) {
            if (s == null) s = "";
            if (s.length() < minLen) minLen = s.length();
        }

        int low = 0, high = minLen;
        while (low < high) {
            int mid = (low + high + 1) / 2;
            if (isCommonPrefix(strs, mid)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return strs[0].substring(0, low);
    }

    private boolean isCommonPrefix(String[] strs, int len) {
        String base = strs[0] == null ? "" : strs[0];
        String prefix = base.substring(0, len);
        for (int i = 1; i < strs.length; i++) {
            String s = strs[i] == null ? "" : strs[i];
            if (!s.startsWith(prefix)) return false;
        }
        return true;
    }
}
package algorithm.longestCommonPrefix;

/**
 * Min/Max trick: LCP of all strings equals the LCP of lexicographic min and max.
 * Time: O(n·L), Space: O(1)
 */
public class MinMaxStrategy implements LCPStrategy {

    @Override
    public String compute(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String mn = strs[0], mx = strs[0];
        for (String s : strs) {
            if (s == null) s = ""; // robust to nulls
            if (s.compareTo(mn) < 0) mn = s;
            if (s.compareTo(mx) > 0) mx = s;
        }

        int i = 0, lim = Math.min(mn.length(), mx.length());
        while (i < lim && mn.charAt(i) == mx.charAt(i)) i++;
        return mn.substring(0, i);
    }
}
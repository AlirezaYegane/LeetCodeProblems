package longestPalindrome;

import java.util.Objects;

public final class ExpandCenterPalindromeFinder implements PalindromeFinder {

    @Override
    public PalindromeResult find(String s) {
        if (s == null) throw new IllegalArgumentException("input is null");
        int n = s.length();
        if (n < 2) return new PalindromeResult(s, 0, n);

        int bestStart = 0;
        int bestEnd = 0;

        for (int i = 0; i < n; i++) {
            int len1 = expand(s, i, i);        // odd
            int len2 = expand(s, i, i + 1); // even
            int len = Math.max(len1, len2);

            if (len > bestEnd - bestStart + 1) {
                bestStart = i - (len - 1) / 2;
                bestEnd = i + len / 2;
            }
        }
        String val = s.substring(bestStart, bestEnd + 1);
        return new PalindromeResult(val, bestStart, val.length());
    }

    private int expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return r - l - 1;
    }
}

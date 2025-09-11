package longestPalindrome;

import java.util.Objects;

public final class ManacherPalindromeFinder implements PalindromeFinder {
    private final Preprocessor preprocessor;

    public ManacherPalindromeFinder(Preprocessor preprocessor) {
        this.preprocessor = Objects.requireNonNull(preprocessor, "preprocessor");
    }

    @Override
    public PalindromeResult find(String s) {
        if (s == null) throw new IllegalArgumentException("input is null");
        if (s.length() < 2) return new PalindromeResult(s, 0, s.length());

        char[] t = preprocessor.transform(s);
        int n = t.length;

        int[] p = new int[n];
        int center = 0;
        int right = 0;
        int bestLen = 0;
        int bestCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            if (i < right) {
                p[i] = Math.min(right - i, p[mirror]);
            }

            while (t[i + (1 + p[i])] == t[i - (1 + p[i])]) {
                p[i]++;
            }

            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }

            if (p[i] > bestLen) {
                bestLen = p[i];
                bestCenter = i;
            }
        }

        int start = (bestCenter - bestLen) / 2;
        return new PalindromeResult(s.substring(start, start + bestLen), start, bestLen);
    }
}

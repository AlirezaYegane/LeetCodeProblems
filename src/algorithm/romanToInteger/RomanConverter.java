package algorithm.romanToInteger;


/**
 * High-performance Roman → Integer converter.
 * Single pass, O(n) time, O(1) space.
 * Uses a 26-slot lookup table (A–Z) instead of a HashMap to minimize per-char overhead.
 */
public final class RomanConverter implements RomanNumeralService {

    // Lookup table for 'A'..'Z' (index = ch - 'A')
    private static final int[] VALS = new int[26];
    static {
        VALS['I' - 'A'] = 1;
        VALS['V' - 'A'] = 5;
        VALS['X' - 'A'] = 10;
        VALS['L' - 'A'] = 50;
        VALS['C' - 'A'] = 100;
        VALS['D' - 'A'] = 500;
        VALS['M' - 'A'] = 1000;
    }

    @Override
    public int convert(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input must be non-empty.");
        }

        final int n = s.length();
        int total = 0;

        // Validate and convert in a single left→right pass
        for (int i = 0; i < n - 1; i++) {
            char c = s.charAt(i);
            int cur = valueOf(c);
            int nxt = valueOf(s.charAt(i + 1));
            total += (cur < nxt) ? -cur : cur;
        }
        total += valueOf(s.charAt(n - 1));
        return total;
    }

    /** Returns the numeric value for a Roman letter; throws on invalid input. */
    private static int valueOf(char c) {
        if (c < 'A' || c > 'Z') {
            throw new IllegalArgumentException("Invalid character: " + c);
        }
        int v = VALS[c - 'A'];
        if (v == 0) {
            throw new IllegalArgumentException("Invalid Roman symbol: " + c);
        }
        return v;
    }
}
package longestSubstringWithoutRepeatingCharacters;

public final class AsciiStrategy implements Strategy {
    private static final int ASCII_SIZE = 128;

    @Override
    public int length(String s) {
        int[] last = new int[ASCII_SIZE];
        int L = 0, best = 0;
        for (int R = 0; R < s.length(); R++) {
            int idx = s.charAt(R);
            if (idx >= ASCII_SIZE) {
                return new UnicodeStrategy().length(s);
            }
            L = Math.max(L, last[idx]);
            best = Math.max(best, R - L + 1);
            last[idx] = R + 1;
        }
        return best;
    }

    @Override
    public String substring(String s) {
        int[] last = new int[ASCII_SIZE];
        int L = 0, best = 0, bestL = 0;
        for (int R = 0; R < s.length(); R++) {
            int idx = s.charAt(R);
            if (idx >= ASCII_SIZE) {
                return new UnicodeStrategy().substring(s);
            }
            L = Math.max(L, last[idx]);
            if (R - L + 1 > best) {
                best = R - L + 1;
                bestL = L;
            }
            last[idx] = R + 1;
        }
        return s.substring(bestL, bestL + best);
    }
}
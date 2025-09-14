package algorithm.longestSubstringWithoutRepeatingCharacters;

import java.util.HashMap;
import java.util.Map;

public final class UnicodeStrategy implements Strategy {
    @Override
    public int length(String s) {
        Map<Character, Integer> last = new HashMap<>(); // char -> lastIndex+1
        int L = 0, best = 0;
        for (int R = 0; R < s.length(); R++) {
            char ch = s.charAt(R);
            Integer seen = last.get(ch);
            if (seen != null) L = Math.max(L, seen);
            best = Math.max(best, R - L + 1);
            last.put(ch, R + 1);
        }
        return best;
    }

    @Override
    public String substring(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int L = 0, best = 0, bestL = 0;
        for (int R = 0; R < s.length(); R++) {
            char ch = s.charAt(R);
            Integer seen = last.get(ch);
            if (seen != null) L = Math.max(L, seen);
            if (R - L + 1 > best) { best = R - L + 1; bestL = L; }
            last.put(ch, R + 1);
        }
        return s.substring(bestL, bestL + best);
    }
}

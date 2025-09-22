package algorithm.integertoRoman;

/**
 * Greedy converter using the standard 13 ordered pairs.
 * This keeps the algorithm simple, fast, and O(1).
 */
public final class GreedyRomanConverter implements RomanNumeralConverter {

    private static final int[] VALUES = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };
    private static final String[] SYMBOLS = {
            "M",  "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    @Override
    public String intToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("num must be in [1, 3999]");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
            if (num == 0) break;
        }
        return sb.toString();
    }
}
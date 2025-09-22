package algorithm.integertoRoman;

/** Abstraction for converting integers to Roman numerals. */
public interface RomanNumeralConverter {
    /**
     * Converts a positive integer in [1, 3999] to a Roman numeral string.
     */
    String intToRoman(int num);
}
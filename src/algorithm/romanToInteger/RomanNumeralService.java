package algorithm.romanToInteger;

/** Service contract for Roman → Integer conversion. */
public interface RomanNumeralService {
    /**
     * Converts a Roman numeral (valid, uppercase) to an integer.
     * @param s Roman numeral string, e.g., "MCMXCIV"
     * @return integer value
     * @throws IllegalArgumentException if s is null/empty or contains invalid characters
     */
    int convert(String s);
}
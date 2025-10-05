package algorithm.letterCombinationsofaPhoneNumber;

import java.util.Optional;

public interface DigitToLetters {
    /**
     * Returns the letters mapped to a given digit '2'..'9'.
     * If the digit is unsupported, returns Optional.empty().
     */
    Optional<String> lettersOf(char digit);

    /**
     * Validates that all characters are in ['2'..'9'] and mappable.
     */
    default boolean isValidDigits(String digits) {
        if (digits == null) return false;
        for (char c : digits.toCharArray()) {
            if (lettersOf(c).isEmpty()) return false;
        }
        return true;
    }
}
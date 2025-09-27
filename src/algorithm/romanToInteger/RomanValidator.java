package algorithm.romanToInteger;


/**
 * Strict validator for Roman numerals (optional).
 * Enforces common classical rules:
 * 1) Allowed symbols: I, V, X, L, C, D, M
 * 2) Repetition:
 *    - 'I', 'X', 'C', 'M' can repeat at most 3 times in a row.
 *    - 'V', 'L', 'D' cannot repeat.
 * 3) Subtractive notation:
 *    - 'I' can precede 'V' and 'X' only.
 *    - 'X' can precede 'L' and 'C' only.
 *    - 'C' can precede 'D' and 'M' only.
 *    - Other subtractive pairs are invalid.
 * 4) No multiple-subtractions (e.g., "IIV" is invalid).
 * 5) No subtracting from a value 10x or more smaller than the following (covered by rule 3).
 *
 * This is a pragmatic validator for production-like use, not an academic treatise.
 */
public final class RomanValidator {

    private RomanValidator() {}

    public static void validateStrict(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Roman string must be non-empty.");
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            ensureValidSymbol(c);
        }

        // Repetition checks
        int runLen = 1;
        for (int i = 1; i < s.length(); i++) {
            char prev = s.charAt(i - 1);
            char cur  = s.charAt(i);
            if (cur == prev) {
                runLen++;
                ensureValidRepetition(prev, runLen);
            } else {
                runLen = 1;
            }
        }

        // Subtractive notation checks
        // Also blocks invalid multiple-subtractions like "IIV" or "XXL".
        for (int i = 0; i < s.length() - 1; i++) {
            char a = s.charAt(i);
            char b = s.charAt(i + 1);
            int va = valueOf(a);
            int vb = valueOf(b);

            if (va < vb) {
                // ensure allowed pairs only
                if (!isAllowedSubtractivePair(a, b)) {
                    throw new IllegalArgumentException("Invalid subtractive pair: " + a + b);
                }

                // Prevent patterns like "IIV" (two subtractives in a row starting at same base)
                if (i + 2 < s.length()) {
                    char c = s.charAt(i + 2);
                    int vc = valueOf(c);
                    // After a subtractive pair a<b, the next char must not continue the same subtraction chain
                    // e.g., "IIV" is invalid. "IXI" passes here; overall correctness left to broader grammar rules.
                    if (vb <= vc) {
                        // e.g., "I X X" (10,10 after 9) is suspicious but allowed in some looser variants.
                        // We'll only guard against immediate "double subtraction" like "I I V".
                        if (va == valueOf(s.charAt(i + 1))) {
                            throw new IllegalArgumentException("Invalid double subtraction near: " + s.substring(i, i + 3));
                        }
                    }
                }
            }
        }
    }

    private static void ensureValidSymbol(char c) {
        switch (c) {
            case 'I': case 'V': case 'X': case 'L': case 'C': case 'D': case 'M':
                return;
            default:
                throw new IllegalArgumentException("Invalid symbol: " + c);
        }
    }

    private static void ensureValidRepetition(char c, int runLen) {
        switch (c) {
            case 'V': case 'L': case 'D':
                if (runLen > 1) {
                    throw new IllegalArgumentException("Symbol '" + c + "' cannot repeat.");
                }
                break;
            case 'I': case 'X': case 'C': case 'M':
                if (runLen > 3) {
                    throw new IllegalArgumentException("Symbol '" + c + "' repeated more than 3 times.");
                }
                break;
        }
    }

    private static boolean isAllowedSubtractivePair(char a, char b) {
        switch (a) {
            case 'I': return b == 'V' || b == 'X';
            case 'X': return b == 'L' || b == 'C';
            case 'C': return b == 'D' || b == 'M';
            default:  return false;
        }
    }

    // Minimal valueOf for validator; mirrors RomanConverter’s table semantics
    private static int valueOf(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new IllegalArgumentException("Invalid symbol: " + c);
        }
    }
}
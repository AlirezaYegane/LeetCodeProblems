package algorithm.longestPalindrome;

public record PalindromeResult(String value, int start, int length) {
    public static PalindromeResult empty() {
        return new PalindromeResult("", 0, 0);
    }
}
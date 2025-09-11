package longestPalindrome;

public interface Preprocessor {
    /** Like : "babad" -> "^#b#a#b#a#d#$" */
    char[] transform(String s);
}
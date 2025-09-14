package algorithm.longestSubstringWithoutRepeatingCharacters;

public class Main {
    public static void main(String[] args) {
        String s = (args.length > 0) ? args[0] : "abcabcbb";

        int len  = LongestSubstring.lengthOfLongestSubstring(s);
        String sub = LongestSubstring.longestSubstring(s);

        System.out.printf("len=%d, sub=\"%s\"%n", len, sub);
    }
}

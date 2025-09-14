package algorithm.longestSubstringWithoutRepeatingCharacters;


public interface Strategy {
    int length(String s);
    String substring(String s);
}
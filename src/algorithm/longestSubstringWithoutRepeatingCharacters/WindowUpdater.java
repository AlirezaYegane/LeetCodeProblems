package algorithm.longestSubstringWithoutRepeatingCharacters;


public interface WindowUpdater {
    int advance(int L, int R, char ch);
}

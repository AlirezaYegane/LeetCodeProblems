package algorithm.longestPalindrome;

public class HashPreprocessor implements Preprocessor {

    @Override
    public char[] transform(String s) {
        char[] res = new char[2 * s.length() + 3];
        int idx = 0;
        res[idx++] = '^';
        for (int i = 0; i < s.length(); i++) {
            res[idx++] = '#';
            res[idx++] = s.charAt(i);
        }
        res[idx++] = '#';
        res[idx++] = '$';
        return res;
    }
}

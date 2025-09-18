package algorithm.palindromeNumber;

public final class StringPalindrome implements PalindromeStrategy {

    @Override
    public String name() { return "string"; }

    @Override
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        String s = Integer.toString(x);
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) return false;
        }
        return true;
    }
}
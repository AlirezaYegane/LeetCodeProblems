package algorithm.palindromeNumber;


public final class HalfReversePalindrome implements PalindromeStrategy {

    @Override
    public String name() { return "half"; }

    @Override
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;

        int reversedHalf = 0;
        while (x > reversedHalf) {
            int d = x % 10;
            reversedHalf = reversedHalf * 10 + d;
            x /= 10;
        }
        // even length: x == reversedHalf
        // odd length : x == reversedHalf / 10 (drop the middle digit)
        return (x == reversedHalf) || (x == reversedHalf / 10);
    }
}
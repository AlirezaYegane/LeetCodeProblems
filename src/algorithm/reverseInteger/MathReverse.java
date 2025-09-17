package algorithm.reverseInteger;

public class MathReverse implements ReverseStrategy {
    private static final int INT_MIN = -2147483648; // -2^31
    private static final int INT_MAX = 2147483647;  // 2^31 - 1

    @Override
    public int reverse(int x) {
        int rev = 0;
        int sign = (x < 0) ? -1 : 1;
        x = Math.abs(x);

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            if (rev > (INT_MAX - digit) / 10) {
                return 0;
            }
            rev = rev * 10 + digit;
        }

        return sign * rev;
    }
}
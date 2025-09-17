package algorithm.reverseInteger;

public class StringReverse implements ReverseStrategy {
    @Override
    public int reverse(int x) {
        if (x == Integer.MIN_VALUE) return 0;

        boolean neg = x < 0;
        String s = Integer.toString(Math.abs(x));

        // reverse digits
        StringBuilder sb = new StringBuilder(s).reverse();

        int i = 0;
        while (i < sb.length() && sb.charAt(i) == '0') i++;
        String core = (i == sb.length()) ? "0" : sb.substring(i);

        try {
            int val = Integer.parseInt(core);
            return neg ? -val : val;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
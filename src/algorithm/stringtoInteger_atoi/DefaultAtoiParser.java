package algorithm.stringtoInteger_atoi;


public class DefaultAtoiParser implements AtoiParser {

    private static final int INT_MAX = (1 << 31) - 1;
    private static final int INT_MIN = - (1 << 31);

    @Override
    public AtoiResult parse(String s) {
        if (s == null || s.isEmpty()) return new AtoiResult(0, false, 0);

        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' '){
            i++;
        }
        if (i == n){
            return new AtoiResult(0, false, i);
        }

        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        int num = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            if (num > (INT_MAX - digit) / 10) {
                return new AtoiResult(sign == 1 ? INT_MAX : INT_MIN, true, i);
            }
            num = num * 10 + digit;
            i++;
        }
        return new AtoiResult(sign * num, false, i);
    }
}
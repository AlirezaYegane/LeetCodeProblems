package algorithm.validParentheses;

/**
 * O(n) time / O(n) space (worst-case) validator using an array-backed stack.
 * Early exits and switch-based matching for maximum throughput.
 */
public final class ValidParenthesesValidator implements Validator {

    @Override
    public boolean isValid(String s) {
        final int n = s.length();
        if ((n & 1) == 1) return false;   // odd length cannot be valid

        CharStack st = new CharStack(n);

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '[':
                case '{':
                    st.push(c);
                    break;
                case ')':
                    if (st.isEmpty() || st.pop() != '(') return false;
                    break;
                case ']':
                    if (st.isEmpty() || st.pop() != '[') return false;
                    break;
                case '}':
                    if (st.isEmpty() || st.pop() != '{') return false;
                    break;
                default:
                    // If inputs are guaranteed to be only ()[]{}, you could throw instead.
                    return false;
            }
        }
        return st.isEmpty();
    }
}

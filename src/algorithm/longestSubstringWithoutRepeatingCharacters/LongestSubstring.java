package algorithm.longestSubstringWithoutRepeatingCharacters;


public final class LongestSubstring {
    private LongestSubstring() {}

    /** طول بلندترین زیررشته بدون تکرار */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null) throw new IllegalArgumentException("input cannot be null");
        return isAscii(s) ? new AsciiStrategy().length(s) : new UnicodeStrategy().length(s);
    }

    /** خودِ بلندترین زیررشته را برمی‌گرداند */
    public static String longestSubstring(String s) {
        if (s == null) throw new IllegalArgumentException("input cannot be null");
        return isAscii(s) ? new AsciiStrategy().substring(s) : new UnicodeStrategy().substring(s);
    }

    /** اگر خواستی مستقیم یکی از استراتژی‌ها را اجبار کنی: */
    public static Strategy ascii()   { return new AsciiStrategy(); }
    public static Strategy unicode() { return new UnicodeStrategy(); }

    private static boolean isAscii(String s) {
        for (int i = 0; i < s.length(); i++) if (s.charAt(i) > 127) return false;
        return true;
    }
}

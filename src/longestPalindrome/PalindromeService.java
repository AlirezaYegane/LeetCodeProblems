package longestPalindrome;

import java.util.Objects;

public final class PalindromeService {
    private PalindromeFinder finder;

    public PalindromeService(PalindromeFinder finder) {
        this.finder = Objects.requireNonNull(finder, "finder");
    }

    public PalindromeResult findLongest(String s) {
        return finder.find(s);
    }

    public void setFinder(PalindromeFinder finder) {
        this.finder = Objects.requireNonNull(finder, "finder");
    }

    public static PalindromeService manacherDefault() {
        return new PalindromeService(new ManacherPalindromeFinder(new HashPreprocessor()));
    }

    public static PalindromeService expandCenter() {
        return new PalindromeService(new ExpandCenterPalindromeFinder());
    }
}

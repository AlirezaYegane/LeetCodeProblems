package algorithm.palindromeNumber;

import java.util.Objects;

public final class PalindromeService {
    private PalindromeStrategy strategy;

    public PalindromeService(PalindromeStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public PalindromeStrategy getStrategy() { return strategy; }

    public void setStrategy(PalindromeStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public boolean isPalindrome(int x) {
        return strategy.isPalindrome(x);
    }

    public static PalindromeService halfDefault() {
        return new PalindromeService(new HalfReversePalindrome());
    }
}
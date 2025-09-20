package algorithm.regularExpressionMatching;

/**
 * Dynamic Programming matcher:
 * dp[i][j] = does s[0..i) match p[0..j)?
 * Time:  O(m*n)  | Space: O(m*n)
 */
public final class DPRegexMatcher implements IMatcher {

    @Override
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // empty string vs patterns like a*, a*b*, ...
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pj = p.charAt(j - 1);
                if (pj != '*') {
                    dp[i][j] = dp[i - 1][j - 1] &&
                            (pj == '.' || s.charAt(i - 1) == pj);
                } else {
                    // zero occurrence of previous token
                    dp[i][j] = dp[i][j - 2];
                    // one-or-more occurrence of previous token
                    char prev = p.charAt(j - 2);
                    if (prev == '.' || prev == s.charAt(i - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
            }
        }
        return dp[m][n];
    }
}
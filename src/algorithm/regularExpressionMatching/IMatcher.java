package algorithm.regularExpressionMatching;

/** Contract for a regex matcher supporting '.' and '*'. */
public interface IMatcher {
    /**
     * Checks whether the entire string s matches the entire pattern p.
     * Only '.' and '*' (with usual regex meaning) are supported.
     */
    boolean isMatch(String s, String p);
}
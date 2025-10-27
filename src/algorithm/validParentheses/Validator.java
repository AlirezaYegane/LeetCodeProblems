package algorithm.validParentheses;

/**
 * Strategy interface for bracket-validation algorithms.
 */
public interface Validator {
    /**
     * @param s input string containing only ()[]{}
     * @return true if the string is a valid bracket sequence.
     */
    boolean isValid(String s);
}

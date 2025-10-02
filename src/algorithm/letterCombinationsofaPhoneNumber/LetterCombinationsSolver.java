package algorithm.letterCombinationsofaPhoneNumber;


import java.util.List;

public interface LetterCombinationsSolver {
    /**
     * Generates all possible letter combinations for the given digit string.
     * The input must contain only digits in ['2'..'9'].
     */
    List<String> solve(String digits);
}
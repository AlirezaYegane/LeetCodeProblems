package algorithm.letterCombinationsofaPhoneNumber;


import java.util.ArrayList;
import java.util.List;

public final class BacktrackingLetterCombinations implements LetterCombinationsSolver {

    private final DigitToLetters mapper;

    public BacktrackingLetterCombinations(DigitToLetters mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<String> solve(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return res;
        if (!mapper.isValidDigits(digits)) {
            throw new IllegalArgumentException("Digits must be in ['2'..'9'] only.");
        }
        dfs(digits, 0, new StringBuilder(), res);
        return res;
    }

    private void dfs(String digits, int idx, StringBuilder path, List<String> res) {
        if (idx == digits.length()) {
            res.add(path.toString());
            return;
        }
        String letters = mapper.lettersOf(digits.charAt(idx)).orElse("");
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));   // choose
            dfs(digits, idx + 1, path, res);  // explore
            path.deleteCharAt(path.length() - 1); // backtrack
        }
    }
}
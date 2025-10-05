package algorithm.letterCombinationsofaPhoneNumber;


import java.util.Map;
import java.util.Optional;

public final class ClassicPhoneMapper implements DigitToLetters {
    private static final Map<Character, String> MAP = Map.of(
            '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
            '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
    );

    @Override
    public Optional<String> lettersOf(char digit) {
        return Optional.ofNullable(MAP.get(digit));
    }
}

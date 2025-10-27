package algorithm.validParentheses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * -----------------------------------------------------------------------------
 *  Main.java
 * -----------------------------------------------------------------------------
 *  Command-line entry point for the Valid Parentheses validator.
 *
 *  Usage Examples:
 *  ---------------------------------------------------------------------------
 *      ./gradlew run --args="()[]{}"
 *      echo "([{}])" | ./gradlew run
 *
 *  Or to run built-in happy tests:
 *      ./gradlew run --args="--happy-tests"
 *      # or interactively:
 *      # Enter ":test" when prompted
 *
 *  Prints "true" or "false" for single-run mode, and a PASS/FAIL summary for tests.
 * -----------------------------------------------------------------------------
 */
public final class Main {

    public static void main(String[] args) throws Exception {
        // Special mode: run inline happy-tests
        if (args != null && Arrays.asList(args).contains("--happy-tests")) {
            runHappyTests();
            return;
        }

        String input;

        // Option 1: Input passed as command-line argument
        if (args != null && args.length > 0) {
            input = args[0];
        } else {
            // Option 2: Read interactively from console
            System.out.print("Enter a bracket string to validate (or ':test' to run happy-tests): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
            if (input == null) input = "";
        }

        if (":test".equalsIgnoreCase(input.trim())) {
            runHappyTests();
            return;
        }

        // Create an instance of the validator
        Validator validator = new ValidParenthesesValidator();

        // Validate and print the result
        boolean result = validator.isValid(input);

        System.out.println("Input : " + input);
        System.out.println("Valid : " + (result ? "true ✅" : "false ❌"));
    }

    // -------------------------------------------------------------------------
    // Inline Happy Tests (lightweight harness, no external dependencies)
    // -------------------------------------------------------------------------
    private static void runHappyTests() {
        System.out.println("Running happy-tests…");

        Validator v = new ValidParenthesesValidator();

        // test cases: {input, expected}
        Object[][] cases = new Object[][]{
                {"", true},                 // empty is valid
                {"()", true},
                {"()[]{}", true},
                {"(]", false},
                {"([{}])", true},
                {"([)]", false},
                {"(", false},
                {")", false},
                {"(((((((((())))))))))", true},                // deep nesting
                {"((((((((((((((((((((((", false},              // many opens, invalid
                {"()".repeat(2000), true},                      // repeated patterns
                {"[]{}".repeat(1500), true},
                {"(()", false},                                 // odd-length / dangling
                {"([{}])]}", false}
        };

        int passed = 0;
        for (int i = 0; i < cases.length; i++) {
            String s = (String) cases[i][0];
            boolean expected = (boolean) cases[i][1];
            boolean actual = v.isValid(s);

            if (actual == expected) {
                passed++;
                System.out.println(ok(i, s, expected));
            } else {
                System.out.println(fail(i, s, expected, actual));
            }
        }

        System.out.printf("%nSummary: %d/%d tests passed %s%n",
                passed, cases.length, (passed == cases.length ? "✅" : "❌"));
    }

    private static String ok(int idx, String input, boolean expected) {
        return String.format("  [%02d] PASS  input=\"%s\"  expected=%s  actual=%s",
                idx, shorten(input), expected, expected);
    }

    private static String fail(int idx, String input, boolean expected, boolean actual) {
        return String.format("  [%02d] FAIL  input=\"%s\"  expected=%s  actual=%s",
                idx, shorten(input), expected, actual);
    }

    private static String shorten(String s) {
        final int MAX = 80;
        if (s.length() <= MAX) return s;
        return s.substring(0, 60) + "…(" + s.length() + " chars)";
    }
}

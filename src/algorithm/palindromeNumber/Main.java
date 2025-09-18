package algorithm.palindromeNumber;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Palindrome Number (LC#9) - Interactive Tester
 *
 * Features:
 * - Choose algorithm via command (:algo half|string)
 * - Run built-in sample cases (:cases)
 * - Test arbitrary inputs (type integers; 'q' to quit)
 * - Clear help (:help)
 *
 * Examples (interactive):
 *  :algo half
 *  121
 *  -121
 *  10
 *  :cases
 *  :q
 */
public final class Main {

    private static PalindromeService service = PalindromeService.halfDefault();
    private static String currentAlgo = "half";

    private static final List<Integer> SAMPLE_CASES = Arrays.asList(
            0, 1, 5, 9, 10, 11, 22, 121, 12321, 123321, 1001, 101, 1000021, -121
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Palindrome Number — interactive tester");
        printHelp();

        while (true) {
            System.out.print("> ");
            if (!sc.hasNext()) break;

            // Commands start with colon (e.g., :algo half)
            String token = sc.next();
            if (token.startsWith(":")) {
                String cmd = token.substring(1).toLowerCase(Locale.ROOT);
                switch (cmd) {
                    case "algo":
                        if (sc.hasNext()) {
                            String name = sc.next().toLowerCase(Locale.ROOT);
                            switchAlgo(name);
                        } else {
                            System.out.println("Usage: :algo half | string");
                        }
                        break;
                    case "cases":
                        runSampleCases();
                        break;
                    case "help":
                        printHelp();
                        break;
                    case "q":
                        System.out.println("Bye!");
                        return;
                    default:
                        System.out.println("Unknown command. Use :help");
                }
                continue;
            }

            // Otherwise expect integers
            try {
                int x = Integer.parseInt(token);
                boolean ans = service.isPalindrome(x);
                System.out.printf("Input: %d -> %s (algo=%s)%n", x, ans ? "true" : "false", currentAlgo);
            } catch (NumberFormatException nfe) {
                System.out.println("Enter an integer or a command (start with ':'). Try :help");
            }
        }
    }

    private static void switchAlgo(String name) {
        switch (name) {
            case "half":
                service.setStrategy(new HalfReversePalindrome());
                currentAlgo = "half";
                System.out.println("Switched algorithm to: half (optimized, no string)");
                break;
            case "string":
                service.setStrategy(new StringPalindrome());
                currentAlgo = "string";
                System.out.println("Switched algorithm to: string (simple)");
                break;
            default:
                System.out.println("Unknown algo. Use: half | string");
        }
    }

    private static void runSampleCases() {
        System.out.printf("Running %d cases with algo=%s ...%n", SAMPLE_CASES.size(), currentAlgo);
        for (int x : SAMPLE_CASES) {
            boolean ans = service.isPalindrome(x);
            System.out.printf("  %-8d -> %s%n", x, ans ? "true" : "false");
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  :algo half|string   switch algorithm (default: half)");
        System.out.println("  :cases              run sample test cases");
        System.out.println("  :help               show this help");
        System.out.println("  :q                  quit");
        System.out.println("Or just type integers to test them.");
    }
}

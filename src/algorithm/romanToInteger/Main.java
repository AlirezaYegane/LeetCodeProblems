package algorithm.romanToInteger;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main CLI entrypoint (class name: Main).
 *
 * Modes:
 * 1) No args  -> Demo mode (prints sample conversions)
 * 2) Args     -> Converts each arg (e.g., Main MCMXCIV XL)
 * 3) --stdin  -> Reads one Roman numeral per line from STDIN, prints conversion
 * 4) --test   -> Runs built-in test scenarios (no JUnit required)
 * 5) --strict -> Enables strict validation before conversion (can combine with other modes)
 * 6) --help   -> Prints usage help
 *
 * Exit codes: 0 on success; 1 on invalid input; 2 on I/O errors.
 */
public class Main {

    public static void main(String[] args) {
        boolean useStrictValidation = false;
        boolean useStdin = false;
        boolean runTests = false;

        // --- parse args (بدون لامبدا) ---
        List<String> positionals = new ArrayList<>();
        for (String a : args) {
            switch (a) {
                case "--help":
                    printHelp();
                    return;
                case "--strict":
                    useStrictValidation = true;
                    break;
                case "--stdin":
                    useStdin = true;
                    break;
                case "--test":
                    runTests = true;
                    break;
                default:
                    positionals.add(a);
                    break;
            }
        }

        RomanNumeralService service = new RomanConverter();

        try {
            if (runTests) {
                runBuiltInTests(service, useStrictValidation);
                return;
            }

            if (useStdin) {
                fromStdin(service, useStrictValidation);
                return;
            }

            if (!positionals.isEmpty()) {
                // CLI positional args mode
                for (String token : positionals) {
                    String s = token.toUpperCase();
                    if (useStrictValidation) RomanValidator.validateStrict(s);
                    int value = service.convert(s);
                    System.out.printf("%s -> %d%n", s, value);
                }
                return;
            }

            // Demo mode
            demo(service, useStrictValidation);

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe.getMessage());
            System.exit(2);
        }
    }

    private static void demo(RomanNumeralService service, boolean strict) {
        String[] samples = { "III", "LVIII", "MCMXCIV", "XL", "CM", "MMXXV" };
        System.out.println("Demo mode:");
        for (String s : samples) {
            String u = s.toUpperCase();
            if (strict) RomanValidator.validateStrict(u);
            int val = service.convert(u);
            System.out.printf("%s -> %d%n", u, val);
        }
        System.out.println("\nTip: pass --help for usage, or run with arguments, e.g.,");
        System.out.println("  Main MCMXCIV XL");
        System.out.println("Or strict validation:");
        System.out.println("  Main --strict MCMXCIV");
    }

    private static void fromStdin(RomanNumeralService service, boolean strict) throws IOException {
        System.out.println("Reading from STDIN (one Roman numeral per line). Ctrl+D to end.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            String s = line.trim();
            if (s.isEmpty()) continue;
            s = s.toUpperCase();
            if (strict) RomanValidator.validateStrict(s);
            int value = service.convert(s);
            System.out.printf("%s -> %d%n", s, value);
        }
    }

    /** Built-in tests (no JUnit). */
    private static void runBuiltInTests(RomanNumeralService service, boolean strict) {
        System.out.println("Running built-in tests (strict=" + strict + ") ...");

        String[] goods = {
                "I", "II", "III", "IV", "V", "VI", "VIII", "IX",
                "X", "XL", "XLII", "XLIX", "L", "XC", "XCIX",
                "C", "CD", "D", "CM", "M", "MCMXCIV", "MMXXV", "MMM"
        };
        int[] expected = {
                1, 2, 3, 4, 5, 6, 8, 9,
                10, 40, 42, 49, 50, 90, 99,
                100, 400, 500, 900, 1000, 1994, 2025, 3000
        };

        int pass = 0, fail = 0;
        for (int i = 0; i < goods.length; i++) {
            String s = goods[i];
            try {
                String u = s.toUpperCase();
                if (strict) RomanValidator.validateStrict(u);
                int got = service.convert(u);
                if (got != expected[i]) {
                    fail++;
                    System.out.printf("[FAIL] %s -> got %d, expected %d%n", s, got, expected[i]);
                } else {
                    pass++;
                }
            } catch (Exception e) {
                fail++;
                System.out.printf("[FAIL] %s threw %s (%s)%n", s, e.getClass().getSimpleName(), e.getMessage());
            }
        }

        String[] bads = {
                "IIII", "VV", "IC", "IL", "XM", "VX", "IIV", "MCMCM", "ABC", ""
        };
        if (strict) {
            for (String s : bads) {
                try {
                    String u = s.toUpperCase();
                    RomanValidator.validateStrict(u);
                    int got = service.convert(u);
                    fail++;
                    System.out.printf("[FAIL] invalid '%s' passed with value %d%n", s, got);
                } catch (IllegalArgumentException ok) {
                    pass++;
                } catch (Exception e) {
                    fail++;
                    System.out.printf("[FAIL] invalid '%s' wrong exception: %s%n",
                            s, e.getClass().getSimpleName());
                }
            }
        }

        System.out.printf("Tests finished: passed=%d, failed=%d%n", pass, fail);
        if (fail == 0) System.out.println("All tests passed ✅");
        else System.out.println("Some tests failed ❌");
    }

    private static void printHelp() {
        System.out.println("Roman to Integer — CLI");
        System.out.println("Usage:");
        System.out.println("  Main [--strict] [ROMAN ...]");
        System.out.println("  Main --stdin [--strict]");
        System.out.println("  Main --test [--strict]");
        System.out.println("  Main --help");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --strict   Enable strict Roman grammar validation (throws on invalid forms)");
        System.out.println("  --stdin    Read one Roman numeral per line from STDIN");
        System.out.println("  --test     Run built-in test scenarios (no JUnit required)");
        System.out.println("  --help     Show this help");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  Main MCMXCIV XL CM");
        System.out.println("  Main --strict MCMXCIV");
        System.out.println("  echo \"XLII\" | Main --stdin --strict");
        System.out.println("  Main --test --strict");
    }
}
package algorithm.letterCombinationsofaPhoneNumber;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Main
 * -----------------------------------------------------------------------------
 * A professional entry point for the "Letter Combinations of a Phone Number"
 * problem, suitable for GitHub projects and quick demos.
 *
 * USAGE
 *   1) Run interactive mode (no args):
 *         java leetcode.phone.Main
 *      It will prompt: Enter digits [2-9]
 *
 *   2) Run with a specific digits string:
 *         java leetcode.phone.Main 23
 *
 *   3) Run internal tests (no JUnit needed):
 *         java leetcode.phone.Main --test
 *
 * DESIGN
 *   - Uses OOP components:
 *       DigitToLetters (interface), ClassicPhoneMapper (mapping 2..9),
 *       LetterCombinationsSolver (interface),
 *       BacktrackingLetterCombinations (DFS + backtracking).
 *   - This class wires them together, provides CLI/interactive usage,
 *     and a minimal test harness that compares results as sets.
 *
 * NOTE
 *   - Input must contain only digits in ['2'..'9']. Otherwise an
 *     IllegalArgumentException will be thrown by the solver.
 *
 * AUTHOR
 *   YourName (Alireza Yegane) — MIT License (or your license of choice)
 */
public final class Main {

    public static void main(String[] args) {
        DigitToLetters mapper = new ClassicPhoneMapper();
        LetterCombinationsSolver solver = new BacktrackingLetterCombinations(mapper);

        if (args.length > 0) {
            if ("--test".equalsIgnoreCase(args[0])) {
                runAllTests(solver);
                return;
            } else {
                String digits = args[0].trim();
                runOnce(solver, digits);
                return;
            }
        }

        // Interactive mode
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter digits [2-9] (empty to run demo): ");
            String digits = sc.nextLine().trim();
            if (digits.isEmpty()) {
                runDemo(solver);
            } else {
                runOnce(solver, digits);
            }
        }
    }

    // ------------------------------- Demos -----------------------------------

    private static void runDemo(LetterCombinationsSolver solver) {
        System.out.println("\nDemo cases:");
        runCase(solver, "23");
        runCase(solver, "2");
        runCase(solver, "79");
        runCase(solver, ""); // edge case: empty
    }

    private static void runOnce(LetterCombinationsSolver solver, String digits) {
        runCase(solver, digits);
    }

    private static void runCase(LetterCombinationsSolver solver, String digits) {
        System.out.println("\n> digits = \"" + digits + "\"");
        try {
            Instant t0 = Instant.now();
            List<String> combos = solver.solve(digits);
            Instant t1 = Instant.now();
            System.out.println("Combinations (" + combos.size() + "): " + combos);
            System.out.println("Elapsed: " + Duration.between(t0, t1).toMillis() + " ms");
        } catch (IllegalArgumentException iae) {
            System.out.println("Input error: " + iae.getMessage());
        }
    }

    // ------------------------------- Tests -----------------------------------

    /**
     * Minimal test harness (no external frameworks). Uses set-equality since
     * any order is valid for this LeetCode problem.
     */
    private static void runAllTests(LetterCombinationsSolver solver) {
        System.out.println("Running tests...\n");

        int passed = 0;
        int total = 0;

        total++; passed += testEqualsAsSets(
                "TC1: digits=23",
                solver.solve("23"),
                List.of("ad","ae","af","bd","be","bf","cd","ce","cf")
        );

        total++; passed += testEqualsAsSets(
                "TC2: digits=2",
                solver.solve("2"),
                List.of("a","b","c")
        );

        total++; passed += testEqualsAsSets(
                "TC3: digits=\"\" (empty)",
                solver.solve(""),
                List.of()
        );

        total++; passed += testThrows(
                "TC4: invalid contains 1",
                () -> solver.solve("21"),
                IllegalArgumentException.class
        );

        total++; passed += testEqualsAsSets(
                "TC5: digits=79 (4x4=16 combos)",
                solver.solve("79"),
                cartesian(List.of("pqrs"), List.of("wxyz"))
        );

        System.out.printf("%nRESULT: passed %d / %d tests%n", passed, total);
        if (passed != total) {
            System.exit(1);
        }
    }

    // Utility: build cartesian product for expected lists (for readability)
    private static List<String> cartesian(List<String> group1, List<String> group2) {
        List<String> out = new ArrayList<>();
        for (char a : group1.get(0).toCharArray()) {
            for (char b : group2.get(0).toCharArray()) {
                out.add("" + a + b);
            }
        }
        return out;
    }

    // Assertion helpers
    private static int testEqualsAsSets(String name, List<String> actual, List<String> expected) {
        Set<String> A = new HashSet<>(actual);
        Set<String> E = new HashSet<>(expected);
        boolean ok = A.equals(E);
        printResult(name, ok, "Expected set=" + E + ", Actual set=" + A);
        return ok ? 1 : 0;
    }

    private static int testThrows(String name, ThrowingRunnable r, Class<? extends Throwable> exType) {
        boolean ok = false;
        try {
            r.run();
            ok = false; // should not reach
        } catch (Throwable t) {
            ok = exType.isInstance(t);
        }
        printResult(name, ok, "Expecting exception: " + exType.getSimpleName());
        return ok ? 1 : 0;
    }

    private static void printResult(String name, boolean ok, String details) {
        String mark = ok ? "✅ PASS" : "❌ FAIL";
        System.out.println(mark + "  " + name);
        if (!ok) System.out.println("    " + details);
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
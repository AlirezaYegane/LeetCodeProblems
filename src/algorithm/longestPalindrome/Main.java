package algorithm.longestPalindrome;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Test-friendly Main:
 * - Choose algorithm via CLI arg or interactive command
 * - Run built-in sample cases (--cases)
 * - Run on arbitrary inputs (CLI args or interactive)
 * - Show execution time and result metadata (start, len)
 * <p>
 * Examples:
 * java longestPalindrome.Main --algo=manacher babad cbbd
 * java longestPalindrome.Main --cases
 * java longestPalindrome.Main          (interactive mode)
 * <p>
 * Interactive commands:
 * :algo manacher     or    :algo expand
 * :cases             run sample cases
 * :meta on|off       toggle metadata (start,len,time)
 * :help              help
 * :q                 quit
 */
public final class Main {

    private static PalindromeService service = PalindromeService.manacherDefault();
    private static String currentAlgo = "manacher";
    private static boolean showMeta = true;

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            // parse flags
            for (String a : args) {
                if (a.startsWith("--algo=")) {
                    switchAlgo(a.substring("--algo=".length()));
                } else if (a.equals("--cases")) {
                    runCases();
                    return;
                }
            }
            // treat non-flag args as input strings
            List<String> inputs = Arrays.stream(args)
                    .filter(s -> !s.startsWith("--"))
                    .toList();
            if (!inputs.isEmpty()) {
                for (String s : inputs) runOnce(s);
                return;
            }
        }
        // interactive mode
        interactiveLoop();
    }

    /* ======================= Interactive loop ======================= */

    private static void interactiveLoop() {
        printBanner();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.equals(":q")) break;
            if (line.equals(":help")) {
                printHelp();
                continue;
            }
            if (line.equals(":cases")) {
                runCases();
                continue;
            }
            if (line.startsWith(":meta")) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    showMeta = parts[1].equalsIgnoreCase("on");
                    System.out.println("meta: " + (showMeta ? "ON" : "OFF"));
                } else {
                    System.out.println("usage: :meta on|off");
                }
                continue;
            }
            if (line.startsWith(":algo")) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) switchAlgo(parts[1]);
                else System.out.println("usage: :algo manacher|expand");
                continue;
            }

            // run on user input
            runOnce(line);
        }
        System.out.println("bye 👋");
    }

    /* ======================= Core execution ======================= */

    private static void runOnce(String s) {
        long t0 = System.nanoTime();
        PalindromeResult res = service.findLongest(s);
        long t1 = System.nanoTime();

        // main output
        System.out.println(res.value());

        // optional metadata
        if (showMeta) {
            double ms = (t1 - t0) / 1_000_000.0;
            System.out.printf(Locale.US,
                    "[algo=%s] start=%d, len=%d, time=%.3f ms%n",
                    currentAlgo, res.start(), res.length(), ms);
        }
    }

    /* ======================= Algorithm switching ======================= */

    private static void switchAlgo(String name) {
        String n = name.toLowerCase(Locale.ROOT);
        switch (n) {
            case "manacher", "m" -> {
                service = PalindromeService.manacherDefault();
                currentAlgo = "manacher";
                System.out.println("✔ algorithm: Manacher (O(n))");
            }
            case "expand", "expandcenter", "e" -> {
                service = PalindromeService.expandCenter();
                currentAlgo = "expand";
                System.out.println("✔ algorithm: Expand-Around-Center (O(n^2), O(1))");
            }
            default -> System.out.println("unknown algo: " + name + " (use: manacher|expand)");
        }
    }

    /* ======================= Sample cases ======================= */

    private static void runCases() {
        System.out.println("Running sample cases (" + currentAlgo + "):");
        List<String> samples = List.of(
                "babad",            // bab | aba
                "cbbd",             // bb
                "a",                // a
                "ac",               // a | c
                "racecar",          // racecar
                "forgeeksskeegfor", // geeksskeeg
                "abacdfgdcaba",     // aba
                "aaaaaa",           // aaaaaa
                "abccba"            // abccba
        );
        for (String s : samples) {
            System.out.println("input: " + s);
            runOnce(s);
            System.out.println("---");
        }
    }

    /* ======================= UI/Help ======================= */

    private static void printBanner() {
        System.out.println("Longest Palindrome — interactive tester");
        System.out.println("current algo: " + currentAlgo + "  |  type :help for commands");
    }

    private static void printHelp() {
        System.out.println("""
                commands:
                  :algo manacher       switch to Manacher (O(n))
                  :algo expand         switch to Expand-Around-Center (O(n^2))
                  :cases               run sample cases
                  :meta on|off         toggle start,len,time output
                  :help                this help
                  :q                   quit
                """);
    }
}

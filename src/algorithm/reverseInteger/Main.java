package algorithm.reverseInteger;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Reverse Integer — interactive tester
 *
 * Features:
 * - Choose algorithm via CLI flag or interactive command
 * - Run built-in sample cases (--cases)
 * - Run on arbitrary inputs (CLI args or interactive)
 * - Show execution time (and overflow-safe result)
 *
 * Examples:
 *   java algorithm.reverseinteger.Main --algo=math 123 -123 120
 *   java algorithm.reverseinteger.Main --cases
 *   java algorithm.reverseinteger.Main            (interactive)
 *
 * Interactive commands:
 *   :algo math|string    switch algorithm
 *   :cases               run sample cases
 *   :meta on|off         toggle timing metadata
 *   :help                show help
 *   :q                   quit
 */
public final class Main {

    private static ReverseService service = new ReverseService(new MathReverse());
    private static String currentAlgo = "math";
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
            // treat non-flag args as input integers
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
                else System.out.println("usage: :algo math|string");
                continue;
            }

            // run on user input (can be a single int or space-separated ints)
            for (String tok : line.split("\\s+")) {
                if (!tok.isEmpty()) runOnce(tok);
            }
        }
        System.out.println("bye 👋");
    }

    /* ======================= Core execution ======================= */

    private static void runOnce(String token) {
        Integer x = tryParseInt(token);
        if (x == null) {
            System.out.println("invalid integer: " + token);
            return;
        }

        long t0 = System.nanoTime();
        int res = service.execute(x);
        long t1 = System.nanoTime();

        // main output
        System.out.println(res);

        // optional metadata
        if (showMeta) {
            double ms = (t1 - t0) / 1_000_000.0;
            System.out.printf(Locale.US,
                    "[algo=%s] input=%d, time=%.3f ms%n",
                    currentAlgo, x, ms);
        }
    }

    private static Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }

    /* ======================= Algorithm switching ======================= */

    private static void switchAlgo(String name) {
        String n = name.toLowerCase(Locale.ROOT);
        switch (n) {
            case "math", "m" -> {
                service.setStrategy(new MathReverse());
                currentAlgo = "math";
                System.out.println("✔ algorithm: Math (digit-by-digit, O(log10 n), O(1))");
            }
            case "string", "s" -> {
                // Optional: needs a StringReverse implementation that checks 32-bit overflow and returns 0 if overflow
                service.setStrategy(new StringReverse());
                currentAlgo = "string";
                System.out.println("✔ algorithm: String (uses toString/parseInt, overflow-safe)");
            }
            default -> System.out.println("unknown algo: " + name + " (use: math|string)");
        }
    }

    /* ======================= Sample cases ======================= */

    private static void runCases() {
        System.out.println("Running sample cases (" + currentAlgo + "):");
        List<Integer> samples = List.of(
                123,       // 321
                -123,      // -321
                120,       // 21
                0,         // 0
                10,        // 1
                -10,       // -1
                1534236469, // overflow -> 0
                1463847412, // overflow -> 0
                -1563847412 // overflow -> 0
        );
        for (Integer x : samples) {
            System.out.println("input: " + x);
            runOnce(String.valueOf(x));
            System.out.println("---");
        }
    }

    /* ======================= UI/Help ======================= */

    private static void printBanner() {
        System.out.println("Reverse Integer — interactive tester");
        System.out.println("current algo: " + currentAlgo + "  |  type :help for commands");
    }

    private static void printHelp() {
        System.out.println("""
                commands:
                  :algo math            switch to Math (digit-by-digit)
                  :algo string          switch to String-based
                  :cases                run sample cases
                  :meta on|off          toggle timing output
                  :help                 this help
                  :q                    quit
                """);
    }
}

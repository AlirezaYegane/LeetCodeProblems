package algorithm.zigzagConversion;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Test-friendly Main for Zigzag Conversion.
 *
 * Features:
 * - Choose algorithm via CLI arg or interactive command
 * - Run built-in sample cases (--cases)
 * - Run on arbitrary inputs (pairs of args: <s> <numRows>)
 * - Interactive mode with commands, and per-run prompts
 * - Show execution time and result metadata (len/time)
 *
 * Examples:
 *   java algorithm.zigzag.Main --algo=cycle "PAYPALISHIRING" 3
 *   java algorithm.zigzag.Main --cases
 *   java algorithm.zigzag.Main          (interactive mode)
 *
 * Interactive commands:
 *   :algo cycle|sim            switch algorithm (cycle = cycle arithmetic, sim = simulated up/down)
 *   :cases                     run sample cases
 *   :meta on|off               toggle metadata (len,time)
 *   :help                      help
 *   :q                         quit
 */
public final class Main {

    private static ZigzagService service = ZigzagService.cycleDefault();
    private static String currentAlgo = "cycle";
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
            // treat non-flag args as pairs: <s> <numRows>
            List<String> inputs = Arrays.stream(args)
                    .filter(s -> !s.startsWith("--"))
                    .toList();

            if (!inputs.isEmpty()) {
                if (inputs.size() % 2 != 0) {
                    System.out.println("⚠ need pairs: <s> <numRows>. Last arg ignored.");
                }
                for (int i = 0; i + 1 < inputs.size(); i += 2) {
                    String s = inputs.get(i);
                    int rows = parseRows(inputs.get(i + 1));
                    if (rows <= 0) {
                        System.out.println("✖ invalid numRows: " + inputs.get(i + 1));
                        continue;
                    }
                    runOnce(s, rows);
                }
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
            if (line.equals(":help")) { printHelp(); continue; }
            if (line.equals(":cases")) { runCases(); continue; }
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
                else System.out.println("usage: :algo cycle|sim");
                continue;
            }

            // If it's not a command: treat as the string s
            String s = line;
            System.out.print("numRows = ");
            String rline = sc.nextLine().trim();
            int rows = parseRows(rline);
            if (rows <= 0) {
                System.out.println("✖ invalid numRows: " + rline);
                continue;
            }
            runOnce(s, rows);
        }
        System.out.println("bye 👋");
    }

    /* ======================= Core execution ======================= */

    private static void runOnce(String s, int numRows) {
        long t0 = System.nanoTime();
        String res = service.convert(s, numRows);
        long t1 = System.nanoTime();

        // main output
        System.out.println(res);

        // optional metadata
        if (showMeta) {
            double ms = (t1 - t0) / 1_000_000.0;
            System.out.printf(Locale.US,
                    "[algo=%s] len=%d, time=%.3f ms%n",
                    currentAlgo, res.length(), ms);
        }
    }

    /* ======================= Algorithm switching ======================= */

    private static void switchAlgo(String name) {
        String n = name.toLowerCase(Locale.ROOT);
        switch (n) {
            case "cycle", "c" -> {
                service = ZigzagService.cycleDefault();
                currentAlgo = "cycle";
                System.out.println("✔ algorithm: Cycle (O(n), mirror mapping)");
            }
            case "sim", "simulated", "s" -> {
                service = ZigzagService.simulatedDefault();
                currentAlgo = "sim";
                System.out.println("✔ algorithm: Simulated up/down (O(n))");
            }
            default -> System.out.println("unknown algo: " + name + " (use: cycle|sim)");
        }
    }

    /* ======================= Sample cases ======================= */

    private static void runCases() {
        System.out.println("Running sample cases (" + currentAlgo + "):");
        // LeetCode samples + a few extras
        List<Case> samples = List.of(
                new Case("PAYPALISHIRING", 3, "PAHNAPLSIIGYIR"),
                new Case("PAYPALISHIRING", 4, "PINALSIGYAHRPI"),
                new Case("A", 1, "A"),
                new Case("AB", 1, "AB"),
                new Case("AB", 2, "AB"),
                new Case("HELLOZIGZAG", 4, null)
        );
        for (Case c : samples) {
            System.out.println("input: s=\"" + c.s + "\", numRows=" + c.rows);
            long t0 = System.nanoTime();
            String out = service.convert(c.s, c.rows);
            long t1 = System.nanoTime();
            System.out.println("output: " + out);
            if (c.expect != null) {
                System.out.println("expect: " + c.expect + "  " + (c.expect.equals(out) ? "✓" : "✗"));
            }
            if (showMeta) {
                double ms = (t1 - t0) / 1_000_000.0;
                System.out.printf(Locale.US, "len=%d, time=%.3f ms%n", out.length(), ms);
            }
            System.out.println("---");
        }
    }

    private record Case(String s, int rows, String expect) {}

    /* ======================= UI/Help ======================= */

    private static void printBanner() {
        System.out.println("Zigzag Conversion — interactive tester");
        System.out.println("current algo: " + currentAlgo + "  |  type :help for commands");
    }

    private static void printHelp() {
        System.out.println("""
                commands:
                  :algo cycle           switch to Cycle (O(n), mirror mapping)
                  :algo sim             switch to Simulated up/down (O(n))
                  :cases                run sample cases
                  :meta on|off          toggle len,time output
                  :help                 this help
                  :q                    quit
                Input (interactive):
                  Type your string s, then you'll be prompted for numRows.
                CLI (non-flag args):
                  Provide pairs: <s> <numRows>, e.g. "PAYPALISHIRING" 3
                """);
    }

    /* ======================= Utils ======================= */

    private static int parseRows(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            return -1;
        }
    }
}

package algorithm.zigzagConversion;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Zigzag Conversion — Main (auto-draw after convert)
 *
 * Flags:
 *   --algo=cycle|sim    choose algorithm
 *   --cases             run sample cases and exit
 *   --draw              force draw on   (default)
 *   --nodraw            disable drawing
 *
 * Interactive commands:
 *   :algo cycle|sim
 *   :cases
 *   :meta on|off
 *   :draw on|off        toggle auto drawing
 *   :help
 *   :q
 */
public final class Main {

    /* ========== State ========== */
    private static ZigzagService service = ZigzagService.cycleDefault();
    private static ZigzagRenderer renderer = new AsciiZigzagRenderer();
    private static String currentAlgo = "cycle";
    private static boolean showMeta = true;
    private static boolean showDraw = true;   // ← NEW: auto-draw after convert

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            // flags
            for (String a : args) {
                if (a.startsWith("--algo=")) {
                    switchAlgo(a.substring("--algo=".length()));
                } else if (a.equals("--cases")) {
                    runCases();
                    return;
                } else if (a.equals("--draw")) {
                    showDraw = true;
                } else if (a.equals("--nodraw")) {
                    showDraw = false;
                }
            }

            // non-flag args are pairs: <s> <numRows>
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
                    runOnce(s, rows);   // ← convert + (optional) draw
                }
                return;
            }
        }

        interactiveLoop();
    }

    /* ========== Interactive loop ========== */
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
                String[] p = line.split("\\s+");
                if (p.length == 2) {
                    showMeta = p[1].equalsIgnoreCase("on");
                    System.out.println("meta: " + (showMeta ? "ON" : "OFF"));
                } else System.out.println("usage: :meta on|off");
                continue;
            }

            if (line.startsWith(":draw")) {
                String[] p = line.split("\\s+");
                if (p.length == 2) {
                    showDraw = p[1].equalsIgnoreCase("on");
                    System.out.println("draw: " + (showDraw ? "ON" : "OFF"));
                } else {
                    System.out.println("usage: :draw on|off");
                }
                continue;
            }

            if (line.startsWith(":algo")) {
                String[] p = line.split("\\s+");
                if (p.length == 2) switchAlgo(p[1]);
                else System.out.println("usage: :algo cycle|sim");
                continue;
            }

            // normal run: first token is s, then prompt for rows
            String s = line;
            System.out.print("numRows = ");
            String rline = sc.nextLine().trim();
            int rows = parseRows(rline);
            if (rows <= 0) {
                System.out.println("✖ invalid numRows: " + rline);
                continue;
            }
            runOnce(s, rows);  // ← convert + auto-draw
        }
        System.out.println("bye 👋");
    }

    /* ========== Execution (convert + optional draw) ========== */
    private static void runOnce(String s, int numRows) {
        long t0 = System.nanoTime();
        String res = service.convert(s, numRows);
        long t1 = System.nanoTime();

        // conversion output
        System.out.println(res);

        // meta
        if (showMeta) {
            double ms = (t1 - t0) / 1_000_000.0;
            System.out.printf(Locale.US,
                    "[algo=%s] len=%d, time=%.3f ms%n",
                    currentAlgo, res.length(), ms);
        }

        // auto-draw (ASCII)
        if (showDraw) {
            System.out.println("--- diagram ---");
            String grid = renderer.render(s, numRows);
            System.out.println(grid);
        }
    }

    /* ========== Algo switching ========== */
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

    /* ========== Sample cases ========== */
    private static void runCases() {
        System.out.println("Running sample cases (" + currentAlgo + "):");
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
            if (showDraw) {
                System.out.println("--- diagram ---");
                System.out.println(renderer.render(c.s, c.rows));
            }
            System.out.println("---");
        }
    }

    private record Case(String s, int rows, String expect) {}

    /* ========== UI/Help ========== */
    private static void printBanner() {
        System.out.println("Zigzag Conversion — interactive tester");
        System.out.println("current algo: " + currentAlgo + "  |  draw: " + (showDraw ? "ON" : "OFF")
                + "  |  type :help for commands");
    }

    private static void printHelp() {
        System.out.println("""
                commands:
                  :algo cycle           switch to Cycle (O(n), mirror mapping)
                  :algo sim             switch to Simulated up/down (O(n))
                  :cases                run sample cases
                  :meta on|off          toggle len,time output
                  :draw on|off          toggle ASCII drawing after convert
                  :help                 this help
                  :q                    quit
                Input (interactive):
                  Type your string s, then you'll be prompted for numRows.
                CLI pairs:
                  <s> <numRows>   e.g. "PAYPALISHIRING" 3
                Flags:
                  --algo=cycle|sim
                  --cases
                  --draw | --nodraw
                """);
    }

    /* ========== Utils ========== */
    private static int parseRows(String text) {
        try { return Integer.parseInt(text.trim()); }
        catch (Exception e) { return -1; }
    }
}
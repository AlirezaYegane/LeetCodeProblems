package algorithm.stringtoInteger_atoi;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * String to Integer (atoi) — interactive tester
 *
 * What this runner provides:
 * - Run built-in sample cases:                --cases
 * - Run on arbitrary inputs (CLI or REPL)
 * - Timing/metadata toggle                    :meta on|off
 * - Clean output (only the integer) + optional meta line
 *
 * Examples:
 *   java stringtoInteger_atoi.Main --cases
 *   java stringtoInteger_atoi.Main "   -42"
 *   java stringtoInteger_atoi.Main     (then use REPL)
 *
 * REPL commands:
 *   :cases        run sample strings
 *   :meta on|off  toggle metadata line
 *   :help         show help
 *   :q            quit
 *
 * Notes:
 * - If your input contains spaces, pass it in quotes on the CLI.
 * - AtoiParser/DefaultAtoiParser/AtoiResult must already be in the same package.
 */
public final class Main {

    private static final AtoiParser parser = new DefaultAtoiParser();
    private static boolean showMeta = true;

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            // flags first
            for (String a : args) {
                if (a.equals("--help") || a.equals("-h")) {
                    printHelp();
                    return;
                }
                if (a.equals("--cases")) {
                    runCases();
                    return;
                }
            }
            // treat non-flag args as inputs
            List<String> inputs = Arrays.stream(args)
                    .filter(s -> !s.startsWith("--"))
                    .toList();
            if (!inputs.isEmpty()) {
                for (String s : inputs) runOnce(s);
                return;
            }
        }
        // interactive mode
        repl();
    }

    /* ======================= REPL ======================= */

    private static void repl() {
        printBanner();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine();

            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;

            // commands
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
                String[] p = line.split("\\s+");
                if (p.length == 2) {
                    showMeta = p[1].equalsIgnoreCase("on");
                    System.out.println("meta: " + (showMeta ? "ON" : "OFF"));
                } else {
                    System.out.println("usage: :meta on|off");
                }
                continue;
            }

            // default: treat the whole line as one input string
            runOnce(line);
        }
        System.out.println("bye 👋");
    }

    /* ======================= Core ======================= */

    private static void runOnce(String input) {
        long t0 = System.nanoTime();
        AtoiResult res = parser.parse(input);
        long t1 = System.nanoTime();

        // main output (LeetCode-style)
        System.out.println(res.value());

        if (showMeta) {
            double ms = (t1 - t0) / 1_000_000.0;
            // اگر AtoiResult شما این فیلدها را ندارد، دو مقدار زیر را مطابق پیاده‌سازی خود تنظیم/حذف کنید.
            String extra = "";
            try {
                extra = String.format(", endIndex=%d, overflowed=%s",
                        res.endIndex(), res.overflowed());
            } catch (Throwable ignored) {
                // فقط value موجود است
            }
            System.out.printf(Locale.US,
                    "[atoi] input=%s, time=%.3f ms%s%n",
                    printable(input), ms, extra);
        }
    }

    /* ======================= Samples ======================= */

    private static void runCases() {
        System.out.println("Running sample cases:");
        List<String> samples = List.of(
                "42",
                "   -42",
                "1337c0d3",
                "0-1",
                "words and 987",
                "+1",
                "   +0 123",
                "-91283472332",  // clamp to INT_MIN
                "91283472332",   // clamp to INT_MAX
                ""               // empty -> 0
        );
        for (String s : samples) {
            System.out.println("input: " + printable(s));
            runOnce(s);
            System.out.println("---");
        }
    }

    /* ======================= UI ======================= */

    private static void printBanner() {
        System.out.println("String to Integer (atoi) — interactive tester");
        System.out.println("type :help for commands");
    }

    private static void printHelp() {
        System.out.println("""
                commands:
                  :cases               run sample strings
                  :meta on|off         toggle timing/metadata line
                  :help                this help
                  :q                   quit

                flags:
                  --cases              run samples then exit
                  --help | -h          show this help

                notes:
                  - For CLI with spaces, wrap the input in quotes (e.g., "   -42").
                  - Output's first line is the integer result (LeetCode style).
                """);
    }

    /* ======================= Utils ======================= */

    private static String printable(String s) {
        // Make whitespace/newlines visible in meta
        return "\"" + s
                .replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\\"")
                + "\"";
    }
}

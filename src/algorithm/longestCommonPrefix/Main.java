package algorithm.longestCommonPrefix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Comprehensive CLI for Longest Common Prefix using Strategy pattern.
 *
 * Usage examples:
 *   # حالت ساده (MinMax پیش‌فرض)
 *   java -cp out lcp.Main flower flow flight
 *
 *   # انتخاب الگوریتم
 *   java -cp out lcp.Main --method=binary interstellar interstate
 *   java -cp out lcp.Main --method=minmax interstellar interstate
 *
 *   # خواندن از فایل (هر خط تعدادی کلمه؛ با space یا , جدا)
 *   java -cp out lcp.Main --file=./words.txt
 *
 *   # اجرای تست‌های داخلی
 *   java -cp out lcp.Main --run-tests
 *
 *   # بنچمارک مصنوعی (تولید ورودی رندوم)
 *   java -cp out lcp.Main --benchmark=10000 --method=minmax
 *
 * نکته: اگر نیاز داری رشته‌ی خالی بدهی، در فایل از یک خط خالی یا یک جداکننده‌ی پشت‌سرهم استفاده کن.
 */
public class Main {

    // -------------------------- Entry Point --------------------------

    public static void main(String[] args) {
        Config cfg = parseArgs(args);

        if (cfg.showHelp) {
            printHelp();
            return;
        }

        if (cfg.runTests) {
            runAllTests();
            return;
        }

        if (cfg.benchmarkN > 0) {
            runBenchmark(cfg);
            return;
        }

        // ورودی کلمات از فایل یا از خود آرگومان‌ها
        String[] words = readWords(cfg);

        // اگر ورودی نداشتیم، دمو اجرا کن
        if (words.length == 0) {
            demo(cfg);
            return;
        }

        // انتخاب استراتژی
        LCPStrategy strategy = makeStrategy(cfg.method);
        Solution sol = new Solution(strategy);

        String lcp = sol.longestCommonPrefix(words);
        System.out.println(lcp);
    }

    // -------------------------- Config & Args --------------------------

    private enum Method { MINMAX, BINARY }

    private static class Config {
        Method method = Method.MINMAX; // default
        String filePath = null;
        boolean runTests = false;
        boolean showHelp = false;
        int benchmarkN = 0;
        int randomStrLen = 12; // طول رشته‌های رندوم در بنچمارک
        String[] inlineWords = new String[0];
    }

    private static Config parseArgs(String[] args) {
        Config cfg = new Config();
        List<String> words = new ArrayList<>();

        for (String a : args) {
            if (a.equals("-h") || a.equals("--help")) {
                cfg.showHelp = true;
            } else if (a.startsWith("--method=")) {
                String m = a.substring("--method=".length()).trim().toLowerCase(Locale.ROOT);
                if (m.equals("minmax")) cfg.method = Method.MINMAX;
                else if (m.equals("binary")) cfg.method = Method.BINARY;
                else {
                    System.err.println("Unknown method: " + m + " (use minmax|binary)");
                    cfg.showHelp = true;
                }
            } else if (a.startsWith("--file=")) {
                cfg.filePath = a.substring("--file=".length()).trim();
            } else if (a.startsWith("--benchmark=")) {
                try {
                    cfg.benchmarkN = Integer.parseInt(a.substring("--benchmark=".length()).trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid --benchmark value. Must be an integer.");
                    cfg.showHelp = true;
                }
            } else if (a.startsWith("--randlen=")) {
                try {
                    cfg.randomStrLen = Integer.parseInt(a.substring("--randlen=".length()).trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid --randlen value. Must be an integer.");
                    cfg.showHelp = true;
                }
            } else if (a.equals("--run-tests")) {
                cfg.runTests = true;
            } else {
                // treat as word
                words.add(a);
            }
        }

        cfg.inlineWords = words.toArray(new String[0]);
        return cfg;
    }

    private static void printHelp() {
        System.out.println(
                "Longest Common Prefix (Strategy-based)\n\n" +
                        "Usage:\n" +
                        "  java -cp out lcp.Main [options] [WORDS...]\n\n" +
                        "Options:\n" +
                        "  --method=minmax|binary   Choose algorithm (default: minmax)\n" +
                        "  --file=PATH              Read words from a file (one or more per line; space/comma separated)\n" +
                        "  --run-tests              Run built-in smoke & edge tests and exit\n" +
                        "  --benchmark=N            Generate N random words and time the algorithm\n" +
                        "  --randlen=L              Length of random words for benchmark (default: 12)\n" +
                        "  -h, --help               Show this help\n\n" +
                        "Examples:\n" +
                        "  java -cp out lcp.Main flower flow flight\n" +
                        "  java -cp out lcp.Main --method=binary interstellar interstate\n" +
                        "  java -cp out lcp.Main --file=./words.txt\n" +
                        "  java -cp out lcp.Main --run-tests\n" +
                        "  java -cp out lcp.Main --benchmark=50000 --method=minmax --randlen=24\n"
        );
    }

    private static LCPStrategy makeStrategy(Method m) {
        return (m == Method.BINARY) ? new BinarySearchStrategy() : new MinMaxStrategy();
    }

    // -------------------------- Input Handling --------------------------

    /**
     * اگر --file داده شده باشد، از فایل می‌خوانیم (هر خط، کلمات با space یا comma جدا).
     * در غیر اینصورت کلمات همان آرگومان‌های باقی‌مانده هستند.
     */
    private static String[] readWords(Config cfg) {
        if (cfg.filePath == null) return cfg.inlineWords;

        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cfg.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // split by comma or whitespace
                String[] tokens = line.trim().split("[,\\s]+");
                for (String t : tokens) {
                    if (!t.isEmpty()) words.add(t);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read file: " + cfg.filePath);
            System.err.println(e.getMessage());
            return new String[0];
        }
        return words.toArray(new String[0]);
    }

    // -------------------------- Demo (no args) --------------------------

    private static void demo(Config cfg) {
        System.out.println("No input provided. Running demo with both strategies...\n");

        String[][] samples = new String[][]{
                {"flower", "flow", "flight"},
                {"dog", "racecar", "car"},
                {""},
                {"interspecies", "interstellar", "interstate"},
                {"a"},
                {"", ""},
                {"prefix", "pre", "prevent", "presentation"},
                {"same", "same", "same"}
        };

        LCPStrategy minmax = new MinMaxStrategy();
        LCPStrategy binary = new BinarySearchStrategy();
        Solution s1 = new Solution(minmax);
        Solution s2 = new Solution(binary);

        for (String[] sample : samples) {
            String ans1 = s1.longestCommonPrefix(sample);
            String ans2 = s2.longestCommonPrefix(sample);
            System.out.println("Input: " + Arrays.toString(sample));
            System.out.println("  MinMax  -> \"" + ans1 + "\"");
            System.out.println("  Binary  -> \"" + ans2 + "\"");
            System.out.println("  Equal?  -> " + ans1.equals(ans2));
            System.out.println();
        }

        System.out.println("Tip: pass words as args, e.g.");
        System.out.println("  java -cp out lcp.Main flower flow flight");
        System.out.println("Or read from file:");
        System.out.println("  java -cp out lcp.Main --file=./words.txt");
        System.out.println("Or run tests:");
        System.out.println("  java -cp out lcp.Main --run-tests");
    }

    // -------------------------- Tests --------------------------

    private static final class TestCase {
        final String name;
        final String[] input;
        final String expected;

        TestCase(String name, String[] input, String expected) {
            this.name = name;
            this.input = input;
            this.expected = expected;
        }
    }

    private static void runAllTests() {
        System.out.println("Running built-in tests (both strategies)...\n");

        List<TestCase> cases = Arrays.asList(
                new TestCase("basic-1", new String[]{"flower","flow","flight"}, "fl"),
                new TestCase("basic-2", new String[]{"dog","racecar","car"}, ""),
                new TestCase("single",  new String[]{"alone"}, "alone"),
                new TestCase("empty-array", new String[]{}, ""),
                new TestCase("has-empty", new String[]{"","b"}, ""),
                new TestCase("all-empty", new String[]{"",""}, ""),
                new TestCase("identical", new String[]{"same","same","same"}, "same"),
                new TestCase("long-common", new String[]{"interspecies","interstellar","interstate"}, "inters"),
                new TestCase("case-sensitive", new String[]{"Case","cAsE"}, ""), // lexicographic is case-sensitive
                new TestCase("unicode", new String[]{"سلام", "سلامت", "سلامانه"}, "سلام")
        );

        LCPStrategy[] strategies = { new MinMaxStrategy(), new BinarySearchStrategy() };
        String[] strategyNames = { "MinMax", "Binary" };

        int total = 0, passed = 0, failed = 0;
        for (int i = 0; i < strategies.length; i++) {
            LCPStrategy st = strategies[i];
            String stName = strategyNames[i];
            System.out.println(">>> Strategy: " + stName);

            for (TestCase tc : cases) {
                total++;
                String got = st.compute(tc.input);
                boolean ok = Objects.equals(got, tc.expected);
                if (ok) {
                    passed++;
                    System.out.printf("  [PASS] %-14s -> \"%s\"%n", tc.name, got);
                } else {
                    failed++;
                    System.out.printf("  [FAIL] %-14s exp=\"%s\" got=\"%s\" input=%s%n",
                            tc.name, tc.expected, got, Arrays.toString(tc.input));
                }
            }
            System.out.println();
        }

        System.out.printf("Summary: total=%d, passed=%d, failed=%d%n", total, passed, failed);
        System.out.println(failed == 0 ? "ALL GOOD ✅" : "Some tests failed ❌");
    }

    // -------------------------- Benchmark --------------------------

    private static void runBenchmark(Config cfg) {
        System.out.printf("Benchmarking %s with N=%d, randLen=%d ...%n",
                cfg.method == Method.BINARY ? "BinarySearch" : "MinMax",
                cfg.benchmarkN, cfg.randomStrLen);

        // تولید داده‌ی رندوم؛ برای کمی واقع‌گرایی، یک پیشوند مشترک کوچک هم تزریق می‌کنیم.
        Random rnd = new Random(42);
        String forcedPrefix = randomAlpha(rnd, 3 + rnd.nextInt(3)); // 3..5
        String[] data = new String[cfg.benchmarkN];
        for (int i = 0; i < cfg.benchmarkN; i++) {
            // 70% با پیشوند مشترک، 30% بدون آن
            boolean share = rnd.nextDouble() < 0.7;
            String base = share ? forcedPrefix : randomAlpha(rnd, 1 + rnd.nextInt(3));
            data[i] = base + randomAlpha(rnd, Math.max(1, cfg.randomStrLen - base.length()));
        }

        LCPStrategy strategy = makeStrategy(cfg.method);
        Solution sol = new Solution(strategy);

        long t0 = System.nanoTime();
        String lcp = sol.longestCommonPrefix(data);
        long t1 = System.nanoTime();

        double ms = (t1 - t0) / 1_000_000.0;
        System.out.printf("Result LCP=\"%s\"%n", lcp);
        System.out.printf("Elapsed: %.3f ms (%,d strings)%n", ms, data.length);
    }

    private static String randomAlpha(Random rnd, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            // a..z
            char c = (char) ('a' + rnd.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }
}

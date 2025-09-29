package algorithm.threeSum;

import java.util.*;

/**
 * Main.java — Professional runner for the 3Sum solution with:
 *  - Example scenarios (mirroring the problem statement)
 *  - CLI arguments to run your own arrays or random fuzz tests
 *  - Invariant checks to validate correctness (sum==0, sorted triplets, uniqueness)
 *  - Lightweight benchmarking
 *
 * Usage (run configurations in IntelliJ):
 *   - No args  : runs built-in examples + a short fuzz test
 *   - --help   : prints help
 *   - --array  : run on a custom array, e.g.
 *       --array "-1,0,1,2,-1,-4"
 *   - Random fuzzing (generate inputs and verify):
 *       --fuzz --iterations 200 --size 50 --min -10 --max 10 --seed 42
 *
 * Notes:
 *   - This file is intentionally verbose with comments and checks for learning and auditing.
 *   - Replace `TwoPointerThreeSumSolver` with any other implementation via `new Solution(new YourSolver())`.
 */
public class Main {

    public static void main(String[] args) {
        if (hasFlag(args, "--help")) {
            printHelp();
            return;
        }

        // Wire up the production solver through the Solution adapter (DI-friendly).
        Solution solution = new Solution(new TwoPointerThreeSumSolver());

        if (hasFlag(args, "--array")) {
            // Run once on a user-provided array
            int[] arr = parseArrayArg(getValue(args, "--array"));
            runOnce("Custom Array", solution, arr, true);
            return;
        }

        // Default path: curated examples + quick fuzz test
        runCuratedExamples(solution);

        // Quick, safe fuzz test unless user opts out
        if (!hasFlag(args, "--no-fuzz")) {
            FuzzConfig cfg = FuzzConfig.fromArgs(args)
                    .withDefaultsIfMissing(100, 50, -20, 20, System.nanoTime());
            runFuzz(solution, cfg);
        }

        log("\nAll checks finished ✅");
    }

    // ===========================
    // Curated Examples
    // ===========================
    private static void runCuratedExamples(Solution solution) {
        log("=== Curated Examples ===");
        runOnce("Example 1", solution, new int[]{-1, 0, 1, 2, -1, -4}, true);
        runOnce("Example 2", solution, new int[]{0, 1, 1}, true);
        runOnce("Example 3", solution, new int[]{0, 0, 0}, true);
        runOnce("Example 4 (extra mixed)", solution, new int[]{-2, 0, 0, 2, 2, -2, -1, 1}, true);
    }

    // ===========================
    // Single Run (with validation & timing)
    // ===========================
    private static void runOnce(String label, Solution solution, int[] input, boolean verbose) {
        int[] copy = Arrays.copyOf(input, input.length); // keep original intact for logging
        long t0 = System.nanoTime();
        List<List<Integer>> triplets = solution.threeSum(copy);
        long t1 = System.nanoTime();

        if (verbose) {
            log("\n" + label);
            log("Input : " + Arrays.toString(input));
            log("Output: " + pretty(triplets));
            log(String.format("Time  : %.3f ms", (t1 - t0) / 1e6));
        }

        // Validate core invariants for peace of mind.
        verifyTriplets(triplets);
    }

    // ===========================
    // Fuzzing (randomized testing)
    // ===========================
    private static void runFuzz(Solution solution, FuzzConfig cfg) {
        log("\n=== Fuzz Testing ===");
        log(cfg.toString());
        Random rnd = new Random(cfg.seed);

        for (int it = 1; it <= cfg.iterations; it++) {
            int n = cfg.size;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = rnd.nextInt(cfg.max - cfg.min + 1) + cfg.min;
            }
            List<List<Integer>> res = solution.threeSum(Arrays.copyOf(arr, n));
            verifyTriplets(res);

            if (it % Math.max(1, cfg.iterations / 10) == 0) {
                log(String.format("  progress: %3d/%d", it, cfg.iterations));
            }
        }
        log("Fuzz tests passed ✅");
    }

    // ===========================
    // Correctness Checks
    // ===========================
    /**
     * Ensures each triplet:
     *  - has size 3
     *  - sums to zero
     *  - is non-decreasing (due to sorted input inside solver)
     *  - and that the overall set has no duplicates
     * Throws AssertionError if any condition is violated.
     */
    private static void verifyTriplets(List<List<Integer>> triplets) {
        Set<String> seen = new HashSet<>();
        for (List<Integer> t : triplets) {
            if (t.size() != 3) {
                throw new AssertionError("Triplet size is not 3: " + t);
            }
            int a = t.get(0), b = t.get(1), c = t.get(2);
            if (a + b + c != 0) {
                throw new AssertionError("Triplet sum is not zero: " + t);
            }
            if (!(a <= b && b <= c)) {
                throw new AssertionError("Triplet is not non-decreasing: " + t);
            }
            String key = a + "," + b + "," + c;
            if (!seen.add(key)) {
                throw new AssertionError("Duplicate triplet found: " + t);
            }
        }
    }

    // ===========================
    // Helpers: formatting, CLI, parsing
    // ===========================
    private static String pretty(List<List<Integer>> triplets) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < triplets.size(); i++) {
            sb.append(triplets.get(i));
            if (i + 1 < triplets.size()) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private static boolean hasFlag(String[] args, String flag) {
        for (String a : args) if (a.equalsIgnoreCase(flag)) return true;
        return false;
    }

    private static String getValue(String[] args, String key) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equalsIgnoreCase(key)) {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException("Missing value for " + key);
    }

    private static int[] parseArrayArg(String csv) {
        String[] parts = csv.trim().split("\\s*,\\s*");
        int[] out = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            out[i] = Integer.parseInt(parts[i]);
        }
        return out;
    }

    private static void log(String s) {
        System.out.println(s);
    }

    private static void printHelp() {
        log("3Sum Runner (Main.java)\n");
        log("Options:");
        log("  --help                       Show this help");
        log("  --array \"a,b,c,...\"          Run on a custom array");
        log("  --fuzz                       Run randomized tests with invariants");
        log("    --iterations <int>         Number of random cases (default 100)");
        log("    --size <int>               Array length for fuzzing (default 50)");
        log("    --min <int>                Min value (default -20)");
        log("    --max <int>                Max value (default 20)");
        log("    --seed <long>              RNG seed (default: system time)");
        log("  --no-fuzz                    Skip fuzz tests in default mode");
        log("\nExamples:");
        log("  (1) Run curated examples:");
        log("      no args");
        log("  (2) Custom array:");
        log("      --array \"-1,0,1,2,-1,-4\"");
        log("  (3) Fuzz 200 cases of size 60 in [-10,10] with fixed seed:");
        log("      --fuzz --iterations 200 --size 60 --min -10 --max 10 --seed 42");
    }

    // ===========================
    // Fuzzing configuration
    // ===========================
    private static class FuzzConfig {
        final Integer iterations;
        final Integer size;
        final Integer min;
        final Integer max;
        final Long seed;

        FuzzConfig(Integer iterations, Integer size, Integer min, Integer max, Long seed) {
            this.iterations = iterations;
            this.size = size;
            this.min = min;
            this.max = max;
            this.seed = seed;
        }

        static FuzzConfig fromArgs(String[] args) {
            Integer it = hasFlag(args, "--fuzz") ? tryParseInt(getOrNull(args, "--iterations")) : null;
            Integer sz = hasFlag(args, "--fuzz") ? tryParseInt(getOrNull(args, "--size")) : null;
            Integer mn = hasFlag(args, "--fuzz") ? tryParseInt(getOrNull(args, "--min")) : null;
            Integer mx = hasFlag(args, "--fuzz") ? tryParseInt(getOrNull(args, "--max")) : null;
            Long sd = hasFlag(args, "--fuzz") ? tryParseLong(getOrNull(args, "--seed")) : null;
            return new FuzzConfig(it, sz, mn, mx, sd);
        }

        FuzzConfig withDefaultsIfMissing(int defIter, int defSize, int defMin, int defMax, long defSeed) {
            return new FuzzConfig(
                    iterations != null ? iterations : defIter,
                    size != null ? size : defSize,
                    min != null ? min : defMin,
                    max != null ? max : defMax,
                    seed != null ? seed : defSeed
            );
        }

        static String getOrNull(String[] args, String key) {
            for (int i = 0; i < args.length - 1; i++) {
                if (args[i].equalsIgnoreCase(key)) return args[i + 1];
            }
            return null;
        }

        static Integer tryParseInt(String v) { return v == null ? null : Integer.parseInt(v); }
        static Long tryParseLong(String v) { return v == null ? null : Long.parseLong(v); }

        @Override public String toString() {
            return "FuzzConfig{" +
                    "iterations=" + iterations +
                    ", size=" + size +
                    ", min=" + min +
                    ", max=" + max +
                    ", seed=" + seed +
                    '}';
        }
    }
}

package algorithm.threeSumClosest;


import java.util.*;

/**
 * Professional-grade Main with:
 *  - CLI modes: demo | stdin | check | bench
 *  - Deterministic test suite (with expected values)
 *  - Property-based randomized tests (cross-check vs brute force)
 *  - Simple micro-benchmark to sense O(n^2) behavior
 *
 * USAGE (examples):
 *  1) Demo (default):
 *     Run without args, or: --mode=demo
 *
 *  2) Provide input via CLI:
 *     --mode=stdin
 *     Then type:
 *       nums:  -1 2 1 -4
 *       target: 1
 *
 *  3) Direct CLI args:
 *     --mode=demo --nums="-1,2,1,-4" --target=1
 *
 *  4) Deterministic checks:
 *     --mode=check
 *
 *  5) Property tests:
 *     --mode=check --random=1 --trials=300 --seed=42
 *
 *  6) Benchmark:
 *     --mode=bench --nStart=100 --nEnd=1000 --step=200 --trials=3 --seed=123
 *
 * Notes:
 *  - Parsing nums supports comma/space separators.
 *  - For reliability, expected values in deterministic tests are computed using a brute-force reference.
 */
public class Main {

    // ---------------------------
    // Reference (for tests only)
    // ---------------------------
    private static class BruteForceSolver implements IThreeSumClosestSolver {
        @Override
        public int threeSumClosest(int[] nums, int target) {
            if (nums == null || nums.length < 3)
                throw new IllegalArgumentException("Array must contain at least 3 elements.");
            int n = nums.length;
            int best = nums[0] + nums[1] + nums[2];
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    for (int k = j + 1; k < n; k++) {
                        int s = nums[i] + nums[j] + nums[k];
                        if (Math.abs(s - target) < Math.abs(best - target)) {
                            best = s;
                        }
                    }
                }
            }
            return best;
        }
    }

    // ---------------------------
    // CLI Parsing
    // ---------------------------
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (String a : args) {
            if (a.startsWith("--")) {
                int eq = a.indexOf('=');
                if (eq > 2) {
                    String k = a.substring(2, eq).trim();
                    String v = a.substring(eq + 1).trim();
                    map.put(k, v);
                } else {
                    map.put(a.substring(2), "1");
                }
            }
        }
        return map;
    }

    private static int[] parseNums(String raw) {
        if (raw == null || raw.isEmpty()) return new int[0];
        raw = raw.replaceAll("[\\[\\]]", " ");
        String[] parts = raw.split("[,\\s]+");
        List<Integer> res = new ArrayList<>();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            res.add(Integer.parseInt(p));
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    // ---------------------------
    // Deterministic Test Suite
    // ---------------------------
    private static void runDeterministicSuite(IThreeSumClosestSolver solver) {
        System.out.println("=== Deterministic Test Suite ===");
        List<int[]> cases = new ArrayList<>();
        List<Integer> targets = new ArrayList<>();

        // Classic / edge-ish cases
        cases.add(new int[]{-1, 2, 1, -4});   targets.add(1);     // expected ~ 2
        cases.add(new int[]{0, 0, 0});        targets.add(1);     // expected ~ 0
        cases.add(new int[]{1, 1, 1, 0});     targets.add(-100);  // expected ~ 2
        cases.add(new int[]{-3, -2, -5, 3, -4}); targets.add(-1); // expected ~ -2
        cases.add(new int[]{5, -7, 3, 2, 9, -1}); targets.add(4);
        cases.add(new int[]{-1000, -1000, -1000, 1000, 999, 998, -999}); targets.add(-1);


        // Compute expected with brute-force (golden)
        BruteForceSolver ref = new BruteForceSolver();
        int pass = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] nums = cases.get(i);
            int target = targets.get(i);
            int expected = ref.threeSumClosest(nums, target);
            int got = solver.threeSumClosest(nums.clone(), target);

            boolean ok = got == expected;
            if (ok) pass++;
            System.out.printf("Case #%d: nums=%s, target=%d  => got=%d, expected=%d  [%s]%n",
                    i + 1, Arrays.toString(nums), target, got, expected, ok ? "PASS" : "FAIL");
        }
        System.out.printf("Passed %d/%d deterministic tests.%n%n", pass, cases.size());
    }

    // ---------------------------
    // Property-based Random Tests
    // ---------------------------
    private static void runPropertyTests(IThreeSumClosestSolver solver, long seed, int trials, int nMin, int nMax, int absVal) {
        System.out.println("=== Property Tests (random) ===");
        Random rnd = new Random(seed);
        BruteForceSolver ref = new BruteForceSolver();
        int pass = 0;

        for (int t = 1; t <= trials; t++) {
            int n = nMin + rnd.nextInt(Math.max(1, nMax - nMin + 1));
            if (n < 3) n = 3;
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = rnd.nextInt(2 * absVal + 1) - absVal; // in [-absVal, +absVal]
            }
            int target = rnd.nextInt(2 * absVal + 1) - absVal;

            int exp = ref.threeSumClosest(nums, target);
            int got = solver.threeSumClosest(nums.clone(), target);

            boolean ok = (got == exp);
            if (ok) pass++;
            if (!ok) {
                System.out.printf("Trial #%d FAILED:%n nums=%s%n target=%d%n expected=%d%n got=%d%n",
                        t, Arrays.toString(nums), target, exp, got);
                // Fail-fast is optional; keep running to see more issues.
            }
        }
        System.out.printf("Random tests passed %d/%d (seed=%d).%n%n", pass, trials, seed);
    }

    // ---------------------------
    // Benchmark
    // ---------------------------
    private static void runBenchmark(IThreeSumClosestSolver solver, long seed, int nStart, int nEnd, int step, int trials) {
        System.out.println("=== Benchmark (O(n^2) expected) ===");
        System.out.printf("seed=%d, nStart=%d, nEnd=%d, step=%d, trials=%d%n", seed, nStart, nEnd, step, trials);
        Random rnd = new Random(seed);

        for (int n = nStart; n <= nEnd; n += step) {
            long sumMs = 0L;
            for (int tr = 0; tr < trials; tr++) {
                int[] nums = new int[n];
                for (int i = 0; i < n; i++) nums[i] = rnd.nextInt(2001) - 1000;
                int target = rnd.nextInt(2001) - 1000;

                long t0 = System.nanoTime();
                int res = solver.threeSumClosest(nums, target);
                long t1 = System.nanoTime();
                sumMs += (t1 - t0) / 1_000_000L;

                // Lightweight sanity via tiny mutation:
                if (n >= 3) {
                    nums[0] += 1;
                    int res2 = solver.threeSumClosest(nums, target);
                    // No assert on relation; just make sure it runs OK.
                    if ((res2 < Integer.MIN_VALUE) && (res2 > Integer.MAX_VALUE)) {
                        System.out.print(""); // impossible, just to avoid warnings
                    }
                }
            }
            double avgMs = sumMs / (double) trials;
            System.out.printf("n=%d -> avg time ~ %.3f ms%n", n, avgMs);
        }
        System.out.println();
    }

    // ---------------------------
    // STDIN mode
    // ---------------------------
    private static void runStdin(IThreeSumClosestSolver solver) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter nums (space or comma separated), then target.");
        System.out.print("nums: ");
        String raw = sc.nextLine();
        int[] nums = parseNums(raw);

        System.out.print("target: ");
        int target = Integer.parseInt(sc.nextLine().trim());

        int ans = solver.threeSumClosest(nums, target);
        System.out.println("Closest sum = " + ans);
    }

    // ---------------------------
    // Demo
    // ---------------------------
    private static void runDemo(IThreeSumClosestSolver solver, Map<String, String> args) {
        String numsArg = args.get("nums");
        String targetArg = args.get("target");

        if (numsArg != null && targetArg != null) {
            int[] nums = parseNums(numsArg);
            int target = Integer.parseInt(targetArg);
            System.out.println("Demo (CLI-provided):");
            System.out.println("nums=" + Arrays.toString(nums) + ", target=" + target);
            int res = solver.threeSumClosest(nums, target);
            System.out.println("Closest sum = " + res + "\n");
            return;
        }

        // Default demo cases
        System.out.println("Demo (built-in):");
        int[][] demoNums = {
                {-1, 2, 1, -4},
                {0, 0, 0},
                {1, 1, 1, 0},
                {-3, -2, -5, 3, -4},
                {5, -7, 3, 2, 9, -1}
        };
        int[] demoTargets = {1, 1, -100, -1, 4};
        for (int i = 0; i < demoNums.length; i++) {
            int res = solver.threeSumClosest(demoNums[i].clone(), demoTargets[i]);
            System.out.printf("nums=%s, target=%d -> closest=%d%n",
                    Arrays.toString(demoNums[i]), demoTargets[i], res);
        }
        System.out.println();
    }

    // ---------------------------
    // Main
    // ---------------------------
    public static void main(String[] args) {
        Map<String, String> m = parseArgs(args);
        String mode = m.getOrDefault("mode", "demo").toLowerCase(Locale.ROOT);

        IThreeSumClosestSolver solver = new ThreeSumClosestSolver();

        switch (mode) {
            case "stdin":
                runStdin(solver);
                break;

            case "check": {
                // deterministic tests
                runDeterministicSuite(solver);

                // optional property tests
                boolean doRandom = "1".equals(m.getOrDefault("random", "0"));
                if (doRandom) {
                    long seed = Long.parseLong(m.getOrDefault("seed", "42"));
                    int trials = Integer.parseInt(m.getOrDefault("trials", "200"));
                    int nMin = Integer.parseInt(m.getOrDefault("nMin", "3"));
                    int nMax = Integer.parseInt(m.getOrDefault("nMax", "12"));
                    int absVal = Integer.parseInt(m.getOrDefault("absVal", "50"));
                    runPropertyTests(solver, seed, trials, nMin, nMax, absVal);
                }
                break;
            }

            case "bench": {
                long seed = Long.parseLong(m.getOrDefault("seed", "123"));
                int nStart = Integer.parseInt(m.getOrDefault("nStart", "100"));
                int nEnd = Integer.parseInt(m.getOrDefault("nEnd", "1000"));
                int step = Integer.parseInt(m.getOrDefault("step", "200"));
                int trials = Integer.parseInt(m.getOrDefault("trials", "3"));
                runBenchmark(solver, seed, nStart, nEnd, step, trials);
                break;
            }

            case "demo":
            default:
                runDemo(solver, m);
                break;
        }

        // Quick note for CI/CD pipelines:
        //  - mode=check (with random=1) is a good smoke test in build servers.
        //  - mode=bench is only for local performance sensing.
    }
}
package foursum;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Main مینیمال برای اجرای الگوریتم 4Sum.
 *
 * استفاده:
 *   1) اجرای پیش‌فرض (دمو):
 *        ./gradlew run
 *
 *   2) با ورودی سفارشی:
 *        ./gradlew run --args="--nums 1,0,-1,0,-2,2 --target 0"
 *        ./gradlew run --args="--nums [1,0,-1,0,-2,2] --target 0"
 *
 *   3) راهنما:
 *        ./gradlew run --args="--help"
 *
 * نکته:
 *   - فقط دو فلگ ساده داریم: --nums و --target
 *   - nums به صورت CSV یا داخل براکت‌ها پذیرفته می‌شود.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Map<String, String> flags = parseArgs(args);

            if (flags.containsKey("help")) {
                printHelp();
                return;
            }

            // اگر ورودی دادی، از فلگ‌ها بخوان؛ وگرنه سناریوی پیش‌فرض را اجرا کن.
            int[] nums;
            int target;
            if (flags.containsKey("nums") && flags.containsKey("target")) {
                nums = parseNums(flags.get("nums"));
                target = parseIntStrict(flags.get("target"));
                if (nums.length < 4) {
                    throw new IllegalArgumentException("nums باید حداقل 4 عدد داشته باشد.");
                }
            } else {
                // دمو: نمونه‌ی کلاسیک
                nums = new int[]{1, 0, -1, 0, -2, 2};
                target = 0;
            }

            // ساخت حل‌گر (پیاده‌سازی بهینه: sort + دو حلقه + two-pointer + pruning)
            FourSumSolver solver = new TwoPointerFourSumSolver();

            long t0 = System.nanoTime();
            var result = solver.solve(nums, target);
            long t1 = System.nanoTime();

            // چاپ خروجی خوانا
            System.out.println("nums   = " + Arrays.toString(nums));
            System.out.println("target = " + target);
            System.out.println("count  = " + result.size());
            System.out.println("output = " + pretty(result));
            System.out.printf("time   = %.3f ms%n", (t1 - t0) / 1e6);

        } catch (Exception e) {
            System.err.println("[ERROR] " + e.getMessage());
            System.err.println("Tip: --help");
        }
    }

    // ----------------- کمک‌ها و ابزارهای کوچک -----------------

    private static void printHelp() {
        System.out.println("""
            4SUM – Minimal Main

            Usage:
              # Default demo
              ./gradlew run

              # Custom input (CSV or JSON-like)
              ./gradlew run --args="--nums 1,0,-1,0,-2,2 --target 0"
              ./gradlew run --args="--nums [1,0,-1,0,-2,2] --target 0"

              # Help
              ./gradlew run --args="--help"
            """);
    }

    /**
     * پارس ساده‌ی فلگ‌ها:
     * - فلگ‌های مورد حمایت: --nums ، --target ، --help
     * - اگر فلگی مقدار لازم دارد و ارزش بعدی‌اش با -- شروع شود، خطاست.
     */
    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (!a.startsWith("--")) continue;
            String key = a.substring(2);

            if (key.equals("help")) {
                map.put("help", "true");
                continue;
            }

            // فلگ‌های نیازمند مقدار: nums, target
            if ((key.equals("nums") || key.equals("target"))) {
                if (i + 1 >= args.length || args[i + 1].startsWith("--")) {
                    throw new IllegalArgumentException("فلگ '--" + key + "' نیاز به مقدار دارد.");
                }
                map.put(key, args[++i]);
            }
        }
        return map;
    }

    /**
     * nums را از CSV یا "[...]" به int[] تبدیل می‌کند.
     * مثال‌های معتبر:
     *   "1,0,-1,0,-2,2"
     *   "[1,0,-1,0,-2,2]"
     */
    private static int[] parseNums(String raw) {
        String s = raw.trim();
        if (s.startsWith("[") && s.endsWith("]")) {
            s = s.substring(1, s.length() - 1).trim();
        }
        if (s.isEmpty()) return new int[0];
        String[] parts = s.split("\\s*,\\s*");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = parseIntStrict(parts[i]);
        }
        return arr;
    }

    private static int parseIntStrict(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("مقدار عددی نامعتبر: '" + s + "'");
        }
    }

    /** نمایش زیبا برای فهرست چهار‌تایی‌ها، مثل [[-2,-1,1,2], [-2,0,0,2], ...] */
    private static String pretty(List<List<Integer>> list) {
        return list.stream()
                .map(q -> q.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .map(s -> "[" + s + "]")
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
package algorithm.containerWithMostWater;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Container With Most Water — CLI runner.
 *
 * Examples:
 *   java container.Main --cases
 *   java container.Main 1,8,6,2,5,4,8,3,7
 *   java container.Main                       (interactive)
 *
 * Interactive commands:
 *   enter a CSV of heights  ->  1,8,6,2,5,4,8,3,7
 *   :cases                   -> run sample cases
 *   :q                       -> quit
 */
public final class Main {

    private static ContainerService service =
            new ContainerService(new TwoPointerStrategy());

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0) {
            if ("--cases".equals(args[0])) {
                runCases();
                return;
            }
            // Single-argument CSV run
            List<Integer> heights = ContainerService.parseHeights(args[0]);
            int ans = service.maxArea(heights);
            System.out.println(ans);
            return;
        }

        // Interactive mode
        System.out.println("Container With Most Water — " +
                "strategy: " + new TwoPointerStrategy().name());
        System.out.println("Type CSV heights (e.g., 1,8,6,2,5,4,8,3,7) or :cases or :q");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String line = br.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals(":q")) break;
                if (line.equals(":cases")) { runCases(); continue; }
                if (line.isEmpty()) continue;

                try {
                    List<Integer> heights = ContainerService.parseHeights(line);
                    int ans = service.maxArea(heights);
                    System.out.println("max area = " + ans);
                } catch (Exception e) {
                    System.out.println("Parse error. Enter CSV like: 1,8,6,2,5,4,8,3,7");
                }
            }
        }
    }

    private static void runCases() {
        caseRun("Example (LC):", "1,8,6,2,5,4,8,3,7", 49); // LeetCode example answer is 49
        caseRun("All equal:", "5,5,5,5,5", 20);
        caseRun("Monotonic increasing:", "1,2,3,4,5,6,7", 12);
        caseRun("Zeros:", "0,0,0,0", 0);
    }

    private static void caseRun(String title, String csv, int expected) {
        List<Integer> h = ContainerService.parseHeights(csv);
        int got = service.maxArea(h);
        System.out.printf("%s\n  heights=%s\n  got=%d  expected=%d\n\n", title, csv, got, expected);
    }
}

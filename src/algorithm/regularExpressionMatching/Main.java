package algorithm.regularExpressionMatching;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Run examples:
 *   java algo.regex.App "aa" "a"        -> false
 *   java algo.regex.App "aa" "a*"       -> true
 *   java algo.regex.App "ab" ".*"       -> true
 *   java algo.regex.App --cases         -> run sample cases
 *   java algo.regex.App                 -> interactive mode
 *
 * Interactive commands:
 *   s=<string>    set input string
 *   p=<pattern>   set pattern
 *   :run          evaluate isMatch(s,p)
 *   :cases        run sample cases
 *   :help         help
 *   :q            quit
 */
public final class Main {

    private static final IMatcher matcher = new DPRegexMatcher();

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            if (args.length == 1 && "--cases".equals(args[0])) {
                runCases();
                return;
            }
            if (args.length == 2) {
                System.out.println(matcher.isMatch(args[0], args[1]));
                return;
            }
        }
        interactive();
    }

    private static void runCases() {
        List<String[]> cases = Arrays.asList(
                new String[]{"aa", "a", "false"},
                new String[]{"aa", "a*", "true"},
                new String[]{"ab", ".*", "true"},
                new String[]{"aab", "c*a*b", "true"},
                new String[]{"mississippi", "mis*is*p*.", "false"},
                new String[]{"", "a*", "false"},
                new String[]{"", ".*", "true"},
                new String[]{"aaa", "a*a", "true"}
        );
        System.out.println("Sample cases:");
        for (String[] tc : cases) {
            boolean got = matcher.isMatch(tc[0], tc[1]);
            System.out.printf("s=\"%s\", p=\"%s\" -> %s  (expected %s)%n",
                    tc[0], tc[1], got, tc[2]);
        }
    }

    private static void interactive() {
        Scanner sc = new Scanner(System.in);
        String s = "", p = "";
        System.out.println("Regex Matcher (supports '.' and '*'). Type :help for help.");
        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase(":q")) break;
            if (line.equalsIgnoreCase(":help")) {
                System.out.println("""
                        Commands:
                          s=<string>   set input string (e.g., s=ab)
                          p=<pattern>  set pattern (e.g., p=.*)
                          :run         evaluate isMatch(s,p)
                          :cases       run sample cases
                          :q           quit
                        """);
                continue;
            }
            if (line.equalsIgnoreCase(":cases")) {
                runCases();
                continue;
            }
            if (line.equalsIgnoreCase(":run")) {
                System.out.printf("isMatch(s=\"%s\", p=\"%s\") -> %b%n",
                        s, p, matcher.isMatch(s, p));
                continue;
            }
            if (line.startsWith("s=")) {
                s = line.substring(2);
                System.out.println("s set to: \"" + s + "\"");
                continue;
            }
            if (line.startsWith("p=")) {
                p = line.substring(2);
                System.out.println("p set to: \"" + p + "\"");
                continue;
            }
            System.out.println("Unknown input. Type :help");
        }
        sc.close();
        System.out.println("bye.");
    }
}
package longestPalindrome;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PalindromeService service = PalindromeService.manacherDefault();

        // service.setFinder(new ExpandCenterPalindromeFinder());
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        PalindromeResult res = service.findLongest(s);
        System.out.println(res.value());
        // System.out.printf("value=%s, start=%d, len=%d%n", res.value(), res.start(), res.length());
    }
}
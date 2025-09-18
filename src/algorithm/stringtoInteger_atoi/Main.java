package algorithm.stringtoInteger_atoi;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AtoiParser parser = new DefaultAtoiParser();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        AtoiResult r = parser.parse(s);
        System.out.println(r.value());
        if (r.overflowed()) System.out.println("(clamped)");
        // System.out.println("ended at index: " + r.endIndex());
    }
}
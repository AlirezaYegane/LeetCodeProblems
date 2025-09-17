package algorithm.reverseInteger;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReverseStrategy strategy = new MathReverse();
        ReverseService service = new ReverseService(strategy);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int x = sc.nextInt();

        int result = service.execute(x);
        System.out.println("Reversed: " + result);
    }
}
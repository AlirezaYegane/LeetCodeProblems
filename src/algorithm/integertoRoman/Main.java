package algorithm.integertoRoman;

import java.util.Arrays;

/**
 * CLI runner for the Integer -> Roman converter.
 *
 * Usage:
 *   1) No args  -> runs demo cases
 *   2) With args -> treats each arg as an integer and prints the result
 *
 * Examples:
 *   java integertoroman.Main
 *   java integertoroman.Main 3749 58 1994
 */
public final class Main {

    public static void main(String[] args) {
        RomanNumeralConverter converter = new GreedyRomanConverter();

        if (args.length == 0) {
            // Demo mode
            int[] samples = {3749, 58, 1994, 4, 9, 40, 90, 400, 900, 3888};
            System.out.println("Demo (no args): " + Arrays.toString(samples));
            for (int n : samples) {
                System.out.printf("%4d -> %s%n", n, converter.intToRoman(n));
            }
            return;
        }

        // CLI args mode
        for (String arg : args) {
            try {
                int n = Integer.parseInt(arg.trim());
                System.out.printf("%4d -> %s%n", n, converter.intToRoman(n));
            } catch (NumberFormatException ex) {
                System.err.println("Not an integer: " + arg);
            } catch (IllegalArgumentException ex) {
                System.err.println("Invalid input (" + arg + "): " + ex.getMessage());
            }
        }
    }
}
package algorithm.zigzagConversion;

/** Simulates the pen going down/up between rows. O(n) time, O(numRows) extra space. */
public class SimulatedConverter implements Converter {

    @Override
    public String convert(String s, int numRows) {
        if (s == null) {
            return null;
        }
        int n = s.length();
        if (numRows <= 1 || numRows > n) {
            return s;
        }
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int currRow = 0;
        boolean goingDown = false;
        for (int i = 0; i < n; i++) {
            rows[currRow].append(s.charAt(i));
            if (currRow == 0|| currRow == numRows-1) {
                goingDown = ! goingDown;
                currRow += goingDown ? 1 : -1;
            }
        }
        StringBuilder output = new StringBuilder(n);
        for (StringBuilder sb : rows) {
            output.append(sb);
        }
        return output.toString();
    }
}
package algorithm.zigzagConversion;

/** Uses cycle arithmetic to map indices to rows. O(n) time, O(numRows) extra space. */
public class CycleConverter implements Converter {

    @Override
    public String convert(String s, int numRows) {
        if (s == null) {
            return null;
        }
        int n = s.length();
        if (numRows <= 1 || numRows >= n) {
            return s;
        }

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int cycle = 2 * numRows - 2;
        for (int k = 0; k < n; k++) {
            int r = k % cycle;
            if (r >= numRows){
                r = cycle - r; // mirror on the way up
            }
            rows[r].append(s.charAt(k));
        }

        StringBuilder out = new StringBuilder(n);
        for (StringBuilder sb : rows){
            out.append(sb);
        }
        return out.toString();
    }
}
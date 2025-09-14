package algorithm.zigzagConversion;

import java.util.ArrayList;
import java.util.List;

public class AsciiZigzagRenderer implements ZigzagRenderer {

    @Override
    public String render(String s, int numRows) {
        if (s == null || s.isEmpty()) return System.lineSeparator();
        if (numRows <= 1 || numRows >= s.length()) return s + System.lineSeparator();

        List<StringBuilder> rows = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) rows.add(new StringBuilder());

        int r = 0;
        int c = 0;
        boolean goingDown = true;

        for (int k = 0; k < s.length(); k++) {
            char ch = s.charAt(k);
            putAt(rows.get(r), c, ch);

            if (goingDown) {

                if (r == numRows - 1) {

                    goingDown = false;
                    r--;
                    c++;
                } else {
                    r++;
                }
            } else {

                if (r == 0) {

                    goingDown = true;
                    r++;

                } else {
                    r--;
                    c++;
                }
            }
        }


        int max = rows.stream().mapToInt(StringBuilder::length).max().orElse(0);
        for (StringBuilder sb : rows) while (sb.length() < max) sb.append(' ');

        String nl = System.lineSeparator();
        StringBuilder out = new StringBuilder();
        for (StringBuilder sb : rows) out.append(sb).append(nl);
        return out.toString();
    }

    private static void putAt(StringBuilder sb, int col, char ch) {
        while (sb.length() < col) sb.append(' ');
        if (sb.length() == col) sb.append(ch);
        else sb.setCharAt(col, ch);
    }
}
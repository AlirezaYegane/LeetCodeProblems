package algorithm.zigzagConversion;


public interface ZigzagRenderer {
    /**
     * Render the zigzag pattern as a multi-line ASCII string.
     * Empty cells are filled with spaces.
     */
    String render(String s, int numRows);
}
package algorithm.validParentheses;

/**
 * Ultra-light stack for primitive chars (no boxing, no allocations in loop).
 */
final class CharStack {
    private final char[] a;
    private int top = -1;

    CharStack(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity < 0");
        this.a = new char[capacity];
    }

    void push(char c) { a[++top] = c; }

    char pop() { return a[top--]; }

    boolean isEmpty() { return top == -1; }

    int size() { return top + 1; }
}

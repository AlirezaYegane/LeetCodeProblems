package algorithm.removeNthNodeFromEndofList;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main: demo + lightweight tests for "Remove Nth Node From End".
 *
 * Contents:
 *  - ListNode   : singly linked-list node definition
 *  - ListNodes  : helper utilities (build/print/convert)
 *  - Solution   : one-pass two-pointer algorithm (O(L) time, O(1) space)
 *  - Main       : entry point (demo + simple self-tests)
 */
public class Main {

    /** ===== Data: singly linked-list node ===== */
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) { this.val = val; }
        public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() { return String.valueOf(val); }

        /** Structural equality (convenient for tests). */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ListNode)) return false;
            ListNode a = this, b = (ListNode) o;
            while (a != null && b != null) {
                if (a.val != b.val) return false;
                a = a.next; b = b.next;
            }
            return a == null && b == null;
        }

        @Override
        public int hashCode() {
            int h = 1; ListNode cur = this;
            while (cur != null) { h = 31 * h + Integer.hashCode(cur.val); cur = cur.next; }
            return h;
        }
    }

    /** ===== Helpers for building/printing lists ===== */
    public static final class ListNodes {
        private ListNodes() {}

        /** Build a list from an int varargs. */
        public static ListNode fromArray(int... arr) {
            ListNode dummy = new ListNode(0), cur = dummy;
            for (int v : arr) { cur.next = new ListNode(v); cur = cur.next; }
            return dummy.next;
        }

        /** Convert a list to an int array (for printing/testing). */
        public static int[] toArray(ListNode head) {
            List<Integer> list = new ArrayList<>();
            for (ListNode c = head; c != null; c = c.next) list.add(c.val);
            return list.stream().mapToInt(i -> i).toArray();
        }

        /** Pretty-print a list like [1, 2, 3]. */
        public static String toString(ListNode head) {
            StringBuilder sb = new StringBuilder("[");
            for (ListNode c = head; c != null; c = c.next) {
                sb.append(c.val);
                if (c.next != null) sb.append(", ");
            }
            return sb.append("]").toString();
        }
    }

    /** ===== Solution: dummy head + two pointers, one pass ===== */
    public static class Solution {

        /**
         * Removes the n-th node from the end and returns the new head.
         * Time: O(L), Space: O(1).
         * Policy on invalid input: if n <= 0 or n > length, returns the list unchanged.
         */
        public ListNode removeNthFromEnd(ListNode head, int n) {
            if (head == null || n <= 0) return head;

            ListNode dummy = new ListNode(0, head);
            ListNode fast = dummy, slow = dummy;

            // Advance fast by (n + 1) steps to maintain a fixed gap of n
            for (int i = 0; i < n + 1; i++) {
                if (fast != null) fast = fast.next;
                else return head; // n > length → no change
            }

            // Move both until fast hits the end
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }

            // Delete the target node
            if (slow.next != null) slow.next = slow.next.next;

            return dummy.next;
        }
    }

    /** ===== Tiny test harness (no JUnit required) ===== */
    private static int testsRun = 0, testsPassed = 0;

    private static void expect(ListNode actual, int[] expected, String name) {
        testsRun++;
        int[] arr = ListNodes.toArray(actual);
        boolean ok = Arrays.equals(arr, expected);
        if (ok) {
            testsPassed++;
            System.out.println("[PASS] " + name + "  -> " + Arrays.toString(arr));
        } else {
            System.out.println("[FAIL] " + name + "  -> actual=" + Arrays.toString(arr)
                    + " expected=" + Arrays.toString(expected));
        }
    }

    private static void runTests() {
        Solution sol = new Solution();

        // Example 1: [1,2,3,4,5], n=2  => [1,2,3,5]
        expect(sol.removeNthFromEnd(ListNodes.fromArray(1,2,3,4,5), 2),
                new int[]{1,2,3,5}, "Example#1 remove 2nd from end");

        // Example 2: [1], n=1 => []
        expect(sol.removeNthFromEnd(ListNodes.fromArray(1), 1),
                new int[]{}, "Example#2 remove only node");

        // Example 3: [1,2], n=1 => [1]
        expect(sol.removeNthFromEnd(ListNodes.fromArray(1,2), 1),
                new int[]{1}, "Example#3 remove tail");

        // Remove head when n == length
        expect(sol.removeNthFromEnd(ListNodes.fromArray(10,20,30), 3),
                new int[]{20,30}, "Remove head when n==length");

        // n greater than length: no change
        expect(sol.removeNthFromEnd(ListNodes.fromArray(1,2,3), 5),
                new int[]{1,2,3}, "n > length (no-op)");

        // n <= 0: no change
        expect(sol.removeNthFromEnd(ListNodes.fromArray(7,8), 0),
                new int[]{7,8}, "n <= 0 (no-op)");

        System.out.println("\nTests: " + testsPassed + " / " + testsRun + " passed.");
    }

    /** ===== Entry point ===== */
    public static void main(String[] args) {
        Solution sol = new Solution();

        // --- Demo ---
        ListNode head = ListNodes.fromArray(1, 2, 3, 4, 5);
        int n = 2;
        System.out.println("Input : " + ListNodes.toString(head) + ", n=" + n);
        ListNode result = sol.removeNthFromEnd(head, n);
        System.out.println("Output: " + ListNodes.toString(result)); // [1, 2, 3, 5]

        // --- Run self-tests ---
        System.out.println("\n--- Running self tests ---");
        runTests();

        // NOTE: For larger projects, move classes into separate files
        // (ListNode.java, ListNodes.java, Solution.java) and use JUnit for testing.
    }
}

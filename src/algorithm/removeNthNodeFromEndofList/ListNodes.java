package algorithm.removeNthNodeFromEndofList;

import java.util.ArrayList;
import java.util.List;

/** Utility methods to convert between arrays and linked lists. */
public final class ListNodes {
    private ListNodes() {}

    public static ListNode fromArray(int... arr) {
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : arr) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    public static int[] toArray(ListNode head) {
        List<Integer> list = new ArrayList<>();
        for (ListNode cur = head; cur != null; cur = cur.next) list.add(cur.val);
        return list.stream().mapToInt(i -> i).toArray();
    }

    public static String toString(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode c = head; c != null; c = c.next) {
            sb.append(c.val);
            if (c.next != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
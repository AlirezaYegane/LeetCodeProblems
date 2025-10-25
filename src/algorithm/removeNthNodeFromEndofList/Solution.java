package algorithm.removeNthNodeFromEndofList;

/**
 * One-pass two-pointer solution with a dummy head.
 * Time: O(L), Space: O(1).
 */
public class Solution {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Defensive (اختیاری): اگر ورودی‌ها نامعتبر بودند، همان head برگردد
        if (head == null || n <= 0) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy, slow = dummy;

        // move fast n+1 steps ahead to keep a gap of n
        for (int i = 0; i < n + 1; i++) {
            // اگر n == طول لیست، fast در انتها null می‌شود و مشکلی نیست
            if (fast != null) fast = fast.next;
            else return head; // n بزرگ‌تر از طول: تغییری نده (یا می‌توانید Exception بدهید)
        }

        // move both until fast hits the end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // delete the nth node from end
        if (slow.next != null) slow.next = slow.next.next;

        return dummy.next;
    }
}
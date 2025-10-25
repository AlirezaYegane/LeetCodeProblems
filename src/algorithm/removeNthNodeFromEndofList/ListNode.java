package algorithm.removeNthNodeFromEndofList;

import java.util.Objects;

/** Singly-linked list node. */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    @Override
    public String toString() { return String.valueOf(val); }

    // برای تست‌های راحت‌تر، equals/hashCode بر اساس ترتیب کل لیست:
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
        while (cur != null) { h = 31 * h + Objects.hash(cur.val); cur = cur.next; }
        return h;
    }
}
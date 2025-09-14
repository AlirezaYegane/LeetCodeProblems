package algorithm.addTwoNumbers;

public class Main {
    public static void main(String[] args) {
        ListNode a = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode b = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode result = new Solution().addTwoNumbers(a, b);
        System.out.println(result);
    }
}
package com.example.demo.study.leetcode;

public class AddTwoNumbers {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        if (l1 == null && l2 == null) {
            return null;
        }
        ListNode head = null;
        ListNode current = null;
        while (l1 != null || l2 != null) {
            int num1 = 0;
            int num2 = 0;
            if (l1 != null) {
                num1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                num2 = l2.val;
                l2 = l2.next;
            }
            int sum = num1 + num2 + carry;
            int val = sum % 10;
            carry = sum / 10;
            if (head == null) {
                // 初始节点
                current = head = new ListNode(val);
            } else {
                // 下一节点
                current.next = new ListNode(val);
                // 前进
                current = current.next;
            }
        }
        if (carry > 0) {
            current.next = new ListNode(carry);
        }
        return head;
    }
}

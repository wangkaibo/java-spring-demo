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
        //输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
        //输出：[8,9,9,9,0,0,0,1]
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
            int val = (num1 + num2 + carry) % 10;
            if (num1 + num2 + carry >= 10) {
                carry = (num1 + num2 + carry) / 10;
            } else {
                carry = 0;
            }

            if (head == null) {
                // 初始节点
                head = new ListNode();
                head.val = val;
                head.next = null;
                current = head;
            } else {
                ListNode next = new ListNode();
                next.val = val;
                // 下一节点
                current.next = next;
                // 前进
                current = current.next;
            }
        }
        if (carry > 0) {
            ListNode newNode = new ListNode();
            newNode.val = carry;
            current.next = newNode;
        }
        return head;
    }
}

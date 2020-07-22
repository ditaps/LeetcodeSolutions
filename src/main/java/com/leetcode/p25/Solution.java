package com.leetcode.p25;

import com.leetcode.ListNode;

/**
 * 25. K 个一组翻转链表
 * <p>
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * <p>
 * k 是一个正整数，它的值小于或等于链表的长度。
 * <p>
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 给你这个链表：1->2->3->4->5
 * <p>
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 * <p>
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 * <p>
 *  
 * <p>
 * 说明：
 * <p>
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode head = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6});
        ListNode ans = solution.reverseKGroup(head, 2);
        ListNode.printList(ans);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }
        // 制造一个虚拟的根节点
        ListNode root = new ListNode(0);
        root.next = head;
        // 迭代器
        ListNode it = head;
        // 前一个节点
        ListNode pre = root;
        ListNode next, nextPre, t1, t2;
        int i = 0;
        while (it != null) {
            next = it.next;
            ++i;
            if (i == k) {
                // 达到反转的条件
                // pre -> ( 1 -> 2 -> 3 -> ... -> x ) -> next
                // ==>
                // pre -> ( 1 <- 2 <- 3 <- ... <- x ) -> next
                nextPre = pre.next;
                t1 = pre.next;
                t2 = t1.next;
                while (t2 != next) {
                    ListNode tmp = t2.next;
                    t2.next = t1;
                    t1 = t2;
                    t2 = tmp;
                }
                nextPre.next = t2;
                pre.next = t1;
                i = 0;
                pre = nextPre;
                it = t2;
            } else {
                it = it.next;
            }
        }
        return root.next;
    }

}
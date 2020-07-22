package com.leetcode;

/**
 * 23. 合并K个排序链表
 * ListNode mergeKLists(ListNode[] lists)
 *
 * @author lirongrong
 * @since 2020/7/20
 **/
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    /**
     * 从数据初始化一个链表
     *
     * @param a
     * @return
     */
    public static ListNode fromArray(int[] a) {
        if (a.length == 0) {
            return null;
        }
        ListNode root = new ListNode(a[0]);
        ListNode it = root;
        for (int i = 1; i < a.length; i++) {
            it.next = new ListNode(a[i]);
            it = it.next;
            it.next = null;
        }
        return root;
    }

    /**
     * 输出链表
     *
     * @param root
     */
    public static void printList(ListNode root) {
        ListNode it = root;
        while (it != null) {
            System.out.print(it.val + " -> ");
            it = it.next;
        }
        System.out.println();
    }
}

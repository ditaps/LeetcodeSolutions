package com.leetcode;

import java.util.Scanner;
import java.util.Stack;

/**
 * Leetcode java代码
 *
 * @author lirongrong
 * @since 2019-2-8
 **/
public class SolutionJava {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SolutionJava solution = new SolutionJava();
//        int[] b = new int[] {};
//        int[] a = new int[] {3, 4, 4, 5, 6, 7, 8, 9};
//        double ans = solution.findMedianSortedArrays(a, b);
//        System.out.println(ans);
//        String s = "aa";
//        String ans = solution.longestPalindrome(s);
//        System.out.println(ans);

        //int[] heights = {2, 4, 5, 4, 3, 1};
        //int area = solution.largestRectangleArea(heights);
        //System.out.println(area);

        while (sc.hasNext()) {
            String s = sc.nextLine();
            int res = solution.longestValidParentheses(s);
            System.out.println(res);
        }

    }

    /**
     * 寻找两个有序数组的中位数
     *
     * @param nums1 有序数组
     * @param nums2 有序数组
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0) {
            return nums2.length % 2 == 1 ? nums2[nums2.length / 2]
                    : (nums2[nums2.length / 2] + nums2[nums2.length / 2 - 1]) / 2.0;
        }
        if (nums2.length == 0) {
            return nums1.length % 2 == 1 ? nums1[nums1.length / 2]
                    : (nums1[nums1.length / 2] + nums1[nums1.length / 2 - 1]) / 2.0;
        }
        // +1才好是中间值的下标
        int mid1 = (nums1.length + nums2.length + 1) / 2;
        int mid2 = (nums1.length + nums2.length + 2) / 2;
        return (nums1.length + nums2.length) % 2 == 1 ? findKth(nums1, 0, nums2, 0, mid1)
                : (findKth(nums1, 0, nums2, 0, mid1) +
                findKth(nums1, 0, nums2, 0, mid2)) / 2.0;
    }

    /**
     * 寻找两个有序数组第k大的值
     *
     * @param a
     * @param aBegin
     * @param b
     * @param bBegin
     * @param k
     * @return
     */
    private int findKth(int[] a, int aBegin, int[] b, int bBegin, int k) {
        if (aBegin > a.length - 1) {
            return b[bBegin + k - 1];
        }
        if (bBegin > b.length - 1) {
            return a[aBegin + k - 1];
        }
        if (k == 1) {
            return Math.min(a[aBegin], b[bBegin]);
        }
        int aMidValue = aBegin + k / 2 - 1 < a.length ? a[aBegin + k / 2 - 1] : Integer.MAX_VALUE;
        int bMidvalue = bBegin + k / 2 - 1 < b.length ? b[bBegin + k / 2 - 1] : Integer.MAX_VALUE;
        return aMidValue > bMidvalue
                ? findKth(a, aBegin, b, bBegin + k / 2, k - k / 2)
                : findKth(a, aBegin + k / 2, b, bBegin, k - k / 2);
    }

    /**
     * 最长回文子串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        // 曼切斯特算法
        int len = s.length() * 2 + 1;
        // 转码后的新字符数组
        char[] trans = new char[len + 2];
        // 转码数组对应的以i为中心的最长回文串的半径
        int[] p = new int[len + 2];
        trans[0] = '^';
        trans[1] = '#';
        for (int i = 0; i < s.length(); ++i) {
            trans[i * 2 + 2] = s.charAt(i);
            trans[i * 2 + 3] = '#';
        }
        trans[len + 1] = '\0';
        // 目前为止的中心点id，和右边延伸最长的地方的点位
        int id = 1, mx = 1;
        // 目前最长回文回文串的中心点和半径长度
        int maxId = 0, maxLen = 1;
        for (int i = 1; i < len; ++i) {
            // p[2*id - i] 是对称点的长度，mx-i是剩余长度
            p[i] = mx > i ? Math.min(p[2 * id - i], mx - i) : 1;
            while (trans[i - p[i]] == trans[i + p[i]]) {
                p[i]++;
            }
            if (i + p[i] > mx) {
                mx = i + p[i];
                id = i;
            }
            if (p[i] > maxLen) {
                maxId = i;
                maxLen = p[i];
            }
        }
        int left = (maxId - maxLen + 1) / 2;
        int right = left + maxLen - 1;
        return s.substring(left, right);
    }

    /**
     * 84. 柱状图中最大的矩形
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
     * 处理成单调递增序列，当高度下降的时候，再去递归处理原先的最大面积
     *
     * @param heights n个非负整数
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;
        /** 宽 */
        int width = 0;
        /** 高 */
        int height = 0;
        /** 暂存的递增高度序列 */
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i <= heights.length; ++i) {
            while (!stack.isEmpty() && (i == heights.length || heights[i] <= heights[stack.peek()])) {
                height = heights[stack.pop()];
                width = stack.isEmpty() ? i : i - 1 - stack.peek();
                int area = height * width;
                if (area > maxArea) {
                    maxArea = area;
                }
            }
            stack.push(i);
        }
        return maxArea;
    }

    /**
     * 32. 最长有效括号
     * https://leetcode-cn.com/problems/longest-valid-parentheses/
     * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] d = new int[s.length()];
        int maxLen = 0;
        for (int i = 1; i < s.length(); ++i) {
            int start = i - d[i - 1] - 1;
            if (s.charAt(i) == ')' && start >= 0 && s.charAt(start) == '(') {
                d[i] = d[i - 1] + 2;
                if (start - 1 >= 0) {
                    // 加上前面匹配的
                    d[i] += d[start - 1];
                }
            }
            if (d[i] > maxLen) {
                maxLen = d[i];
            }
        }
        return maxLen;
    }

}

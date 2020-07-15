package com.leetcode;

import java.util.*;

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

        //while (sc.hasNext()) {
        //    String s = sc.nextLine();
        //    int res = solution.longestValidParentheses(s);
        //    System.out.println(res);
        //}

        //boolean ans = solution.isMatch("", "");
        //System.out.println(ans);

        //int[] nums = {7, 2, 6, 1};
        //for (int i = 0; i < nums.length; i++) {
        //    System.out.print(nums[i] + ",");
        //}
        //System.out.println();
        //List<Integer> ans = solution.countSmaller(nums);
        //ans.stream().forEach(x -> System.out.print(x + ","));

        int[][] dungeon = {{-2, -5, 10}, {-1, -2, 5}};
        int ans = solution.calculateMinimumHP(dungeon);
        System.out.println(ans);

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

    /**
     * 44. 通配符匹配
     * https://leetcode-cn.com/problems/wildcard-matching/
     *
     * @param s 字符串
     * @param p 字符模式
     * @return
     */
    public boolean isMatch(String s, String p) {
        if (s == null && p == null) {
            return true;
        } else if (s == null || p == null) {
            return false;
        }
        /**
         * dp[i][j] 表示 s的前i个字符跟 p 的前j个字符的匹配情况
         */
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int j = 0; j < p.length(); ++j) {
            dp[0][j + 1] = dp[0][j] && p.charAt(j) == '*';
        }
        for (int i = 0; i < s.length(); ++i) {
            for (int j = 0; j < p.length(); ++j) {
                if (p.charAt(j) == '*') {
                    dp[i + 1][j + 1] = dp[i][j + 1] || dp[i + 1][j];
                } else {
                    dp[i + 1][j + 1] = (p.charAt(j) == '?' || s.charAt(i) == p.charAt(j)) && dp[i][j];
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    /**
     * 315. 计算右侧小于当前元素的个数
     * https://leetcode-cn.com/problems/count-of-smaller-numbers-after-self/
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        // 当前位置i对应的元素的原始下标
        int[] ind = new int[nums.length];
        // 原始位置 i 对应的右边比它小的数
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ind[i] = i;
        }
        countSmallerMergeSort(nums, 0, nums.length, ind, ans);
        //for (int i = 0; i < nums.length; i++) {
        //    System.out.println(String.format("i = %d, nums[i] = %d, ind[i] = %d, ans[i] = %d", i, nums[i], ind[i], ans[i]));
        //}
        List<Integer> list = new ArrayList<>(nums.length);
        for (int i = 0; i < ans.length; ++i) {
            list.add(ans[i]);
        }
        return list;
    }

    /**
     * 归并排序
     *
     * @param nums
     * @param left
     * @param right
     * @param ind
     * @param ans
     */
    private void countSmallerMergeSort(int[] nums, int left, int right, int[] ind, int[] ans) {
        if (left >= right - 1) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        countSmallerMergeSort(nums, left, mid, ind, ans);
        countSmallerMergeSort(nums, mid, right, ind, ans);
        countSmallerMerge(nums, left, mid, right, ind, ans);
    }

    /**
     * 归并
     *
     * @param nums
     * @param left
     * @param mid
     * @param right
     * @param ind
     * @param ans
     */
    private void countSmallerMerge(int[] nums, int left, int mid, int right, int[] ind, int[] ans) {
        int i = left, j = mid;
        // 排序用到的中间数组
        int[] sorted = new int[right - left];
        int[] tmpInd = new int[right - left];
        int cur = 0;
        int cnt = 0; //交换次数
        while (true) {
            if (i >= mid) {
                break;
            }
            if (j >= right) {
                break;
            }
            while (j < right && nums[i] > nums[j]) {
                cnt++;
                // 修改原始下标位置
                tmpInd[cur] = ind[j];
                sorted[cur++] = nums[j++];
            }
            while (i < mid && j < right && nums[i] <= nums[j]) {
                ans[ind[i]] += cnt;
                // 交换原始下标位置
                tmpInd[cur] = ind[i];
                sorted[cur++] = nums[i++];
            }
        }
        while (i < mid) {
            ans[ind[i]] += cnt;
            // 交换原始下标位置
            tmpInd[cur] = ind[i];
            sorted[cur++] = nums[i++];
        }
        while (j < right) {
            // 修改原始下标位置
            tmpInd[cur] = ind[j];
            sorted[cur++] = nums[j++];
        }
        // 把排序后的结果放回原数组
        for (i = 0; i < cur; ++i) {
            nums[i + left] = sorted[i];
            ind[i + left] = tmpInd[i];
        }
    }

    /**
     * 174. 地下城游戏
     * https://leetcode-cn.com/problems/dungeon-game/
     * 压缩dp，从终点向起点倒推
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int[] dp = new int[dungeon.length + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[dungeon.length - 1] = 1;
        for (int j = dungeon[0].length - 1; j >= 0; j--) {
            for (int i = dungeon.length - 1; i >= 0; i--) {
                dp[i] = Math.max(Math.min(dp[i], dp[i + 1]) - dungeon[i][j], 1);
            }
        }
        return dp[0];
    }

    /**
     * 96. 不同的二叉搜索树
     * https://leetcode-cn.com/problems/unique-binary-search-trees/
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[0] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                // 左边子树的种类*右边子树的种类
                dp[i] += dp[j] * dp[i - 1 - j];
            }
        }
        return dp[n];
        // 卡特兰树解法
    }

}

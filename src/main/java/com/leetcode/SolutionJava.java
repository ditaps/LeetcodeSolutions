package com.leetcode;

/**
 * Leetcode java代码
 *
 * @author lirongrong
 * @since 2019-2-8
 **/
public class SolutionJava {
    public static void main(String[] args) {
        SolutionJava solution = new SolutionJava();
//        int[] b = new int[] {};
//        int[] a = new int[] {3, 4, 4, 5, 6, 7, 8, 9};
//        double ans = solution.findMedianSortedArrays(a, b);
//        System.out.println(ans);
        String s = "aa";
        String ans = solution.longestPalindrome(s);
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
}

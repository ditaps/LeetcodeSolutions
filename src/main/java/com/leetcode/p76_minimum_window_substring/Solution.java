package com.leetcode.p76_minimum_window_substring;

/**
 * 76. 最小覆盖子串
 * <p>
 * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字符的最小子串。
 * <p>
 * 示例：
 * <p>
 * 输入: S = "ADOBECODEBANC", T = "ABC"
 * 输出: "BANC"
 * 说明：
 * <p>
 * 如果 S 中不存这样的子串，则返回空字符串 ""。
 * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-window-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author lirongrong
 * @since 2020/8/1
 **/
public class Solution {

    public static void main(String[] args) {
        String s = "ADOBECODEBANC", t = "ABC";
        String ans = new Solution().minWindow(s, t);
        System.out.println(ans);
    }

    /**
     * 滑动窗口
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (s == null) {
            s = "";
        }
        if (t == null) {
            t = "";
        }
        if ("".equals(s) || "".equals(t)) {
            return "";
        }
        int sLen = s.length(), tLen = t.length();
        int[] tMap = new int[128];
        int[] windowMap = new int[128];
        for (int i = 0; i < t.length(); i++) {
            tMap[t.charAt(i)]++;
        }
        int ansLeft = -1, ansRight = sLen;
        int left = 0, right = -1;
        int matchCnt = 0;
        char[] sChars = s.toCharArray();
        while (true) {
            while (left < sLen && tMap[sChars[left]] == 0) {
                ++left;
            }
            // 延展右边
            for (++right; right < sLen; ++right) {
                char c = sChars[right];
                if (tMap[c] > 0) {
                    windowMap[c]++;
                    ++matchCnt;
                }
                if (matchCnt < tLen) {
                    continue;
                }
                boolean matchFlag = isMatch(tMap, windowMap);
                if (!matchFlag) {
                    continue;
                }
                // 匹配就记录一下
                if (right - left < ansRight - ansLeft) {
                    ansLeft = left;
                    ansRight = right;
                }
                break;
            }
            if (right >= sLen) {
                break;
            }
            // 收缩左边
            for (; left <= right; ++left) {
                char c = sChars[left];
                if (tMap[c] == 0) {
                    continue;
                }
                windowMap[c]--;
                --matchCnt;
                if (windowMap[c] < tMap[c]) {
                    // 去除前相等，说明这时候左边收缩之后不能再收缩了
                    if (right - left < ansRight - ansLeft) {
                        ansLeft = left;
                        ansRight = right;
                    }
                    ++left;
                    break;
                }
            }
        }
        return ansLeft == -1 ? "" : s.substring(ansLeft, ansRight + 1);
    }

    /**
     * 判断一下当前窗口是否可以匹配
     *
     * @param tMap
     * @param windowMap
     * @return
     */
    boolean isMatch(int[] tMap, int[] windowMap) {
        for (int i = 0; i < tMap.length; i++) {
            if (windowMap[i] < tMap[i]) {
                return false;
            }
        }
        return true;
    }
}

package com.leetcode.p6_zigzag_conversion;

/**
 * 6. Z 字形变换
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * string convert(string s, int numRows);
 * 示例 1:
 * <p>
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:
 * <p>
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 * <p>
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zigzag-conversion
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "A";
        int n = 2;
        String ans = solution.convert(s, n);
        System.out.println(ans);
    }

    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        StringBuilder[] trans = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            trans[i] = new StringBuilder();
        }
        int m = numRows + numRows - 2;
        for (int i = 0; i < s.length(); i++) {
            int k = i % m;
            int ind = k < numRows ? k % numRows : numRows - 2 - (k - numRows);
            trans[ind].append(s.charAt(i));
        }
        for (int i = 1; i < numRows; ++i) {
            trans[0].append(trans[i]);
        }
        return trans[0].toString();
    }
}

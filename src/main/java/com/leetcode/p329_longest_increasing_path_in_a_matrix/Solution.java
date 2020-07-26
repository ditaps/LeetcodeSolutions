package com.leetcode.p329_longest_increasing_path_in_a_matrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 329. 矩阵中的最长递增路径
 * <p>
 * 给定一个整数矩阵，找出最长递增路径的长度。
 * <p>
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums =
 * [
 * [9,9,4],
 * [6,6,8],
 * [2,1,1]
 * ]
 * 输出: 4
 * 解释: 最长递增路径为 [1, 2, 6, 9]。
 * 示例 2:
 * <p>
 * 输入: nums =
 * [
 * [3,4,5],
 * [3,2,6],
 * [2,2,1]
 * ]
 * 输出: 4
 * 解释: 最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author lirongrong
 * @since 2020/7/26
 **/
public class Solution {

    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        if (n == 0) {
            return 0;
        }
        int m = matrix[0].length;
        List<MatrixNode> matrixNodeList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrixNodeList.add(new MatrixNode(matrix[i][j], i, j));
            }
        }
        matrixNodeList.sort(Comparator.comparing(MatrixNode::getVal));
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j] = 1;
            }
        }
        int[] dRow = new int[]{-1, 0, 1, 0};
        int[] dCol = new int[]{0, 1, 0, -1};
        matrixNodeList.forEach(node -> {
            for (int i = 0; i < 4; i++) {
                int r = node.row + dRow[i];
                int c = node.col + dCol[i];
                if (r >= 0 && r < n && c >= 0 && c < m && matrix[r][c] > matrix[node.row][node.col]) {
                    dp[r][c] = Math.max(dp[r][c], dp[node.row][node.col] + 1);
                }
            }
        });
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    class MatrixNode {
        public int row;
        public int col;
        public int val;

        MatrixNode(int val, int row, int col) {
            this.val = val;
            this.row = row;
            this.col = col;
        }

        public int getVal() {
            return this.val;
        }
    }
}

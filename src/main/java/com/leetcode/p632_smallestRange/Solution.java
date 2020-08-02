package com.leetcode.p632_smallestRange;

import java.util.*;

/**
 * 632. 最小区间
 * <p>
 * 你有 k 个升序排列的整数数组。找到一个最小区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
 * <p>
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * <p>
 * 示例 1:
 * <p>
 * 输入:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 * 输出: [20,24]
 * 解释:
 * 列表 1：[4, 10, 15, 24, 26]，24 在区间 [20,24] 中。
 * 列表 2：[0, 9, 12, 20]，20 在区间 [20,24] 中。
 * 列表 3：[5, 18, 22, 30]，22 在区间 [20,24] 中。
 * 注意:
 * <p>
 * 给定的列表可能包含重复元素，所以在这里升序表示 >= 。
 * 1 <= k <= 3500
 * -105 <= 元素的值 <= 105
 * 对于使用Java的用户，请注意传入类型已修改为List<List<Integer>>。重置代码模板后可以看到这项改动。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/smallest-range-covering-elements-from-k-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author lirongrong
 * @since 2020/8/2
 **/
public class Solution {

    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        // [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
        nums.add(Arrays.asList(1, 2, 3));
        nums.add(Arrays.asList(1, 2, 3));
        nums.add(Arrays.asList(1, 2, 3));
        int[] ans = new Solution().smallestRange(nums);
        System.out.println(ans[0] + ", " + ans[1]);
    }

    // 使用倒排索引 + 滑动窗口
    public int[] smallestRange(List<List<Integer>> nums) {
        /**
         * 倒排索引，存储元素对应的下标
         */
        Map<Integer, Set<Integer>> revertedIndex = new TreeMap<>();
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                Integer val = nums.get(i).get(j);
                Set<Integer> indexSet = revertedIndex.getOrDefault(val, new HashSet<>());
                indexSet.add(i);
                revertedIndex.put(val, indexSet);
            }
        }
        // 所有的元素的数组
        Integer[] vals = revertedIndex.keySet().toArray(new Integer[revertedIndex.size()]);
        /**
         * 元素的个数
         */
        int valSize = vals.length;
        int numsSize = nums.size();
        /**
         * 当前窗口的包含的数组下标的数量
         */
        int[] indexCnt = new int[nums.size()];
        int cnt = 0;
        int left = 0, right = -1;
        int ansLeft = 0, ansRight = valSize - 1;
        while (right < valSize - 1) {
            // 延展右边界
            while (right < valSize - 1 && cnt < numsSize) {
                ++right;
                for (Integer ind : revertedIndex.get(vals[right])) {
                    ++indexCnt[ind];
                    if (indexCnt[ind] == 1) {
                        ++cnt;
                    }
                }
            }
            if (right == valSize) {
                break;
            }
            // 收缩左边
            while (left <= right && cnt == numsSize) {
                for (Integer ind : revertedIndex.get(vals[left])) {
                    --indexCnt[ind];
                    if (indexCnt[ind] == 0) {
                        --cnt;
                    }
                }
                // b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小
                if (cnt < numsSize && (vals[right] - vals[left] < vals[ansRight] - vals[ansLeft] ||
                        (vals[right] - vals[left] == vals[ansRight] - vals[ansLeft] && vals[left] < vals[ansLeft]))) {
                    ansLeft = left;
                    ansRight = right;
                }
                ++left;
            }
        }
        return new int[]{vals[ansLeft], vals[ansRight]};
    }
}

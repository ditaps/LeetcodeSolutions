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
public class Solution2 {

    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        // [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
        nums.add(Arrays.asList(1, 2, 3));
        nums.add(Arrays.asList(1, 2, 3));
        nums.add(Arrays.asList(1, 2, 3));
        int[] ans = new Solution2().smallestRange(nums);
        System.out.println(ans[0] + ", " + ans[1]);
    }

    // 最小堆
    public int[] smallestRange(List<List<Integer>> nums) {
        int numsSize = nums.size();
        // 表示当前数组的指针在哪里
        int[] ind = new int[numsSize];
        // 最小堆, 元素存的是数组下标，比较器，当前数据的指针的元素
        PriorityQueue<Integer> minPriorityQue = new PriorityQueue<>(
                Comparator.comparing(x -> nums.get(x).get(ind[x])));
        // 存的是当前堆中的最大的元素
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < numsSize; i++) {
            minPriorityQue.offer(i);
            max = Math.max(max, nums.get(i).get(0));
        }
        // 最小元素的所在数组的下标
        int ansMinVal = Integer.MAX_VALUE;
        int minRange = Integer.MAX_VALUE;
        while (true) {
            Integer minInd = minPriorityQue.poll();
            int curRange = max - nums.get(minInd).get(ind[minInd]);
            if (curRange < minRange) {
                ansMinVal = nums.get(minInd).get(ind[minInd]);
                minRange = curRange;
            }
            ++ind[minInd];
            if (ind[minInd] == nums.get(minInd).size()) {
                break;
            }
            minPriorityQue.offer(minInd);
            max = Math.max(max, nums.get(minInd).get(ind[minInd]));
        }
        return new int[]{ansMinVal, ansMinVal + minRange};
    }
}

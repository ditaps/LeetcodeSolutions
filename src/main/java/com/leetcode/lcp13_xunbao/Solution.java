package com.leetcode.lcp13_xunbao;

import java.util.*;

/**
 * LCP 13. 寻宝
 * <p>
 * 我们得到了一副藏宝图，藏宝图显示，在一个迷宫中存在着未被世人发现的宝藏。
 * <p>
 * 迷宫是一个二维矩阵，用一个字符串数组表示。它标识了唯一的入口（用 'S' 表示），和唯一的宝藏地点（用 'T' 表示）。但是，宝藏被一些隐蔽的机关保护了起来。在地图上有若干个机关点（用 'M' 表示），只有所有机关均被触发，才可以拿到宝藏。
 * <p>
 * 要保持机关的触发，需要把一个重石放在上面。迷宫中有若干个石堆（用 'O' 表示），每个石堆都有无限个足够触发机关的重石。但是由于石头太重，我们一次只能搬一个石头到指定地点。
 * <p>
 * 迷宫中同样有一些墙壁（用 '#' 表示），我们不能走入墙壁。剩余的都是可随意通行的点（用 '.' 表示）。石堆、机关、起点和终点（无论是否能拿到宝藏）也是可以通行的。
 * <p>
 * 我们每步可以选择向上/向下/向左/向右移动一格，并且不能移出迷宫。搬起石头和放下石头不算步数。那么，从起点开始，我们最少需要多少步才能最后拿到宝藏呢？如果无法拿到宝藏，返回 -1 。
 * <p>
 * 示例 1：
 * <p>
 * 输入： ["S#O", "M..", "M.T"]
 * <p>
 * 输出：16
 * <p>
 * 解释：最优路线为： S->O, cost = 4, 去搬石头 O->第二行的M, cost = 3, M机关触发 第二行的M->O, cost = 3, 我们需要继续回去 O 搬石头。 O->第三行的M, cost = 4, 此时所有机关均触发 第三行的M->T, cost = 2，去T点拿宝藏。 总步数为16。
 * <p>
 * 示例 2：
 * <p>
 * 输入： ["S#O", "M.#", "M.T"]
 * <p>
 * 输出：-1
 * <p>
 * 解释：我们无法搬到石头触发机关
 * <p>
 * 示例 3：
 * <p>
 * 输入： ["S#O", "M.T", "M.."]
 * <p>
 * 输出：17
 * <p>
 * 解释：注意终点也是可以通行的。
 * <p>
 * 限制：
 * <p>
 * 1 <= maze.length <= 100
 * 1 <= maze[i].length <= 100
 * maze[i].length == maze[j].length
 * S 和 T 有且只有一个
 * 0 <= M的数量 <= 16
 * 0 <= O的数量 <= 40，题目保证当迷宫中存在 M 时，一定存在至少一个 O 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/xun-bao
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author lirongrong
 * @since 2020/7/29
 **/
public class Solution {
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        String[] maze = {".#....M#.M", "#.O...#O#O", ".##..##..#", "...#O#.M.#", "..S#..OO..", "#..T#M.###", ".O.....#.#", "...O..##..", ".....O.#.M", "...#......"};
        Solution solution = new Solution();
        int ans = solution.minimalSteps(maze);
        System.out.println(ans);
    }

    public int minimalSteps(String[] maze) {
        int n = maze.length;
        int m = maze[0].length();
        // 起点，终点
        Point start = null, target = null;
        // 机关
        List<Point> buttons = new ArrayList<>();
        /// 石堆
        List<Point> stones = new ArrayList<>();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length(); j++) {
                switch (maze[i].charAt(j)) {
                    case 'S':
                        start = new Point(i, j);
                        break;
                    case 'T':
                        target = new Point(i, j);
                        break;
                    case 'O':
                        stones.add(new Point(i, j));
                        break;
                    case 'M':
                        buttons.add(new Point(i, j));
                        break;
                }
            }
        }
        int btSize = buttons.size();
        int stoneSize = stones.size();
        // 没有起点，终点
        if (target == null || start == null) {
            return -1;
        }
        // 终点到所有点的最短距离
        int[][] dist = bfs(target, maze, n, m);
        if (btSize == 0) {
            // 没有机关
            return dist[start.x][start.y];
        } else if (stoneSize == 0) {
            // 有机关，且没有石堆
            return -1;
        }
        // m2s[i] = 机关M到起点的最短距离，需要经过石堆O, S - O - M_i
        int[] m2s = new int[btSize];
        Arrays.fill(m2s, -1);
        // m2t[i] = 机关i到终点的距离
        int[] m2t = new int[btSize];
        Arrays.fill(m2t, -1);
        // m2m[i][j] = 机关i到机关j的最短距离，需要经过石堆O，M_i - O - M_j
        int[][] m2m = new int[btSize][btSize];
        for (int i = 0; i < btSize; i++) {
            Arrays.fill(m2m[i], -1);
        }
        // 预处理出最短距离
        for (int i = 0; i < btSize; i++) {
            Point bt = buttons.get(i);
            m2t[i] = dist[bt.x][bt.y];
        }
        for (int k = 0; k < stoneSize; k++) {
            dist = bfs(stones.get(k), maze, n, m);
            for (int i = 0; i < btSize; i++) {
                Point bt1 = buttons.get(i);
                //更新m2s
                if (dist[start.x][start.y] != -1 && dist[bt1.x][bt1.y] != -1 &&
                        (m2s[i] == -1 || m2s[i] > dist[start.x][start.y] + dist[bt1.x][bt1.y])) {
                    m2s[i] = dist[start.x][start.y] + dist[bt1.x][bt1.y];
                }
                for (int j = i + 1; j < btSize; j++) {
                    // 更新m2m
                    Point bt2 = buttons.get(j);
                    if (dist[bt1.x][bt1.y] != -1 && dist[bt2.x][bt2.y] != -1 &&
                            (m2m[i][j] == -1 || m2m[i][j] > dist[bt2.x][bt2.y] + dist[bt1.x][bt1.y])) {
                        m2m[i][j] = dist[bt2.x][bt2.y] + dist[bt1.x][bt1.y];
                        m2m[j][i] = m2m[i][j];
                    }
                }
            }
        }
        for (int i = 0; i < btSize; i++) {
            if (m2s[i] == -1 || m2t[i] == -1) {
                // 到不了起点 或 终点
                return -1;
            }
        }
        // dp[i][j] , i表示机关的开关状态,二进制位表示，j表示当前的所在节点
        int[][] dp = new int[1 << btSize][btSize];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < btSize; i++) {
            dp[1 << i][i] = m2s[i];
        }
        for (int k = 1; k < (1 << btSize); k++) {
            for (int i = 0; i < btSize; i++) {
                if ((k & (1 << i)) != 0) {
                    // 表示当前的k有包含机关i
                    for (int j = 0; j < btSize; j++) {
                        if ((k & (1 << j)) == 0) {
                            // 当前的k不包含机关j
                            int t = k | (1 << j);
                            if (m2m[i][j] != -1 && (dp[t][j] == -1 || dp[t][j] > dp[k][i] + m2m[i][j])) {
                                dp[t][j] = dp[k][i] + m2m[i][j];
                            }
                        }
                    }
                }
            }
        }

        int ans = -1;
        // 表示所有的开关都是开的
        int allBtPos = (1 << btSize) - 1;
        for (int i = 0; i < btSize; i++) {
            if (dp[allBtPos][i] != -1 && (ans == -1 || ans > dp[allBtPos][i] + m2t[i])) {
                ans = dp[allBtPos][i] + m2t[i];
            }
        }
        return ans;
    }

    /**
     * 从一个起点出发，bfs得到所有点的距离
     *
     * @param st
     * @param maze
     * @param n
     * @param m
     * @return
     */
    int[][] bfs(Point st, String[] maze, int n, int m) {
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }
        Queue<Point> queue = new LinkedList<>();
        queue.offer(st);
        dist[st.x][st.y] = 0;
        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if (inbound(nx, ny, n, m) && dist[nx][ny] == -1 && maze[nx].charAt(ny) != '#') {
                    dist[nx][ny] = dist[cur.x][cur.y] + 1;
                    queue.offer(new Point(nx, ny));
                }
            }
        }
        return dist;
    }

    boolean inbound(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

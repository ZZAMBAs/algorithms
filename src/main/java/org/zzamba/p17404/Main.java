package org.zzamba.p17404;

import java.util.*;
import java.util.stream.IntStream;

// dp를 한번에 계산하지 않고 3번 나누어 계산하는 것이 이해하기 더 쉽다. 이러면 dp도 2차원 테이블이 된다.
public class Main {
    static final int COLOR = 3;
    static final int MAX_VAL = 1_000_000_000;

    static int N;
    static int[][] costs;
    static int[][][] dp; // [행][열][시작지] 최소값.

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = Integer.parseInt(sc.nextLine());
        costs = new int[N][COLOR];
        dp = new int[N][COLOR][COLOR];
        IntStream.range(0, N).forEach(i -> costs[i] = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray());
        IntStream.range(0, N).forEach(i -> {
            for (int j = 0; j < COLOR; j++)
                Arrays.fill(dp[i][j], MAX_VAL);
        });
        IntStream.range(0, COLOR).forEach(i -> dp[0][i][i] = costs[0][i]);

        for (int row = 0; row < N - 1; row++) {
            for (int col = 0; col < COLOR; col++) {
                for (int start = 0; start < COLOR; start++) {
                    for (int nextCol = 0; nextCol < COLOR; nextCol++) {
                        if (nextCol == col || (row + 1 == N - 1 && nextCol == start))
                            continue;

                        dp[row + 1][nextCol][start] = Math.min(dp[row + 1][nextCol][start], dp[row][col][start] + costs[row + 1][nextCol]);
                    }
                }
            }
        }

        int minVal = Integer.MAX_VALUE;
        for (int i = 0; i < COLOR; i++)
            for (int j = 0; j < COLOR; j++)
                minVal = Math.min(minVal, dp[N - 1][i][j]);

        System.out.println(minVal);
    }
}

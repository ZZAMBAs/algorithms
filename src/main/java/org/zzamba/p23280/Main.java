package org.zzamba.p23280;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static final int BUTTON_LENGTH = 12;
    static final int INIT_LEFT = 1;
    static final int INIT_RIGHT = 3;
    static final int INFINITY = 100_000_000;

    static int N, A, B;
    static int[] seq;
    static int[][][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        seq = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        dp = new int[N + 1][BUTTON_LENGTH + 1][BUTTON_LENGTH + 1];
        IntStream.rangeClosed(0, N).forEach(i -> IntStream.range(0, BUTTON_LENGTH + 1).forEach(j -> Arrays.fill(dp[i][j], INFINITY)));
        dp[0][INIT_LEFT][INIT_RIGHT] = 0;

        IntStream.range(0, N).forEach(i -> {
            for (int left = 1; left <= BUTTON_LENGTH; left++) {
                for (int right = 1; right <= BUTTON_LENGTH; right++) {
                    if (dp[i][left][right] >= INFINITY)
                        continue;

                    int dest = seq[i];
                    // 왼 손가락 이동
                    dp[i + 1][dest][right] = Math.min(dp[i + 1][dest][right], dp[i][left][right] + calDist(left, dest) + A);

                    // 오른 손가락 이동
                    dp[i + 1][left][dest] = Math.min(dp[i + 1][left][dest], dp[i][left][right] + calDist(right, dest) + B);
                }
            }

        });

        int res = INFINITY;
        for (int i = 1; i <= BUTTON_LENGTH; i++)
            for (int j = 1; j <= BUTTON_LENGTH; j++)
                res = Math.min(res, dp[N][i][j]);
        System.out.print(res);
    }

    static int calDist(int source, int dest) {
        return Math.abs((source - 1) / 3 - (dest - 1) / 3) + Math.abs((source - 1) % 3 - (dest - 1) % 3);
    }
}

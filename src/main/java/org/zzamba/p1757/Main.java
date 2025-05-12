package org.zzamba.p1757;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int[] dist;
    static int[][] dp; // dp[분][지침 지수] : 그 당시까지 가장 멀리 간 거리

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt();
        dist = new int[N];
        dp = new int[N + 1][M + 1];
        IntStream.rangeClosed(0, N).forEach(i -> Arrays.fill(dp[i], -1));
        IntStream.range(0, N).forEach(i -> dist[i] = sc.nextInt());
        dp[0][0] = 0;

        fillDp(N, M);

        System.out.println(dp[N][0]);
    }

    static void fillDp(int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= M; j++) {
                if (dp[i][j] == -1)
                    continue;

                if (j == 0)
                    dp[i + 1][j] = Math.max(dp[i + 1][j], dp[i][j]);

                if (j + 1 <= M)
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j + 1], dp[i][j] + dist[i]); // 달림

                if (i + j <= N)
                    dp[i + j][0] = Math.max(dp[i + j][0], dp[i][j]); // 쉼
            }
        }
    }
}

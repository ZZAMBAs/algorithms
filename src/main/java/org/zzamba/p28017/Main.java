package org.zzamba.p28017;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N, M;
        N = sc.nextInt();
        M = sc.nextInt();
        int[][] dp = new int[N + 1][M];
        for (int i = 0; i <= N; i++)
            Arrays.fill(dp[i], 5000001);
        for (int i = 0; i < M; i++)
            dp[0][i] = 0;

        for (int i = 0; i < N; i++) {
            int[] times = IntStream.range(0, M).map(j -> sc.nextInt()).toArray();
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < M; k++) {
                    if (j == k)
                        continue;

                    dp[i + 1][k] = Math.min(dp[i + 1][k], dp[i][j] + times[k]);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < M; i++)
            res = Math.min(res, dp[N][i]);

        System.out.println(res);
    }
}

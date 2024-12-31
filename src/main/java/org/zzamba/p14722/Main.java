package org.zzamba.p14722;

import java.util.Arrays;
import java.util.Scanner;

// 2차원 DP 풀이가 존재한다.
public class Main {
    static final int KIND = 3;

    static int[][][] dp;
    static int[][] map;
    static int N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new int[N][N];
        dp = new int[N][N][KIND];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                map[i][j] = sc.nextInt();

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                for (int k = 0; k < KIND; k++)
                    dp[i][j][k] = Integer.MIN_VALUE;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0)
                    dp[i][j][0] = Math.max(1, dp[i][j][0]);
                fdp(i, j);
            }

        System.out.print(Math.max(0, Arrays.stream(dp[N - 1][N - 1]).max().getAsInt()));
    }

    static void fdp(int r, int c) {
        int[][] dl = { {1, 0}, {0, 1} };

        for (int[] d : dl) {
            int nextR = r + d[0];
            int nextC = c + d[1];

            if (nextR >= 0 && nextR < N && nextC >= 0 && nextC < N) {
                for (int i = 0; i < KIND; i++)
                    dp[nextR][nextC][i] = Math.max(dp[nextR][nextC][i], dp[r][c][i]);

                int nextMilkKind = map[nextR][nextC];
                dp[nextR][nextC][nextMilkKind] = Math.max(dp[nextR][nextC][nextMilkKind], dp[r][c][(nextMilkKind - 1 + KIND) % KIND] + 1);
            }
        }
    }
}

package org.zzamba.p17208;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        List<Pair> orders = IntStream.range(0, N).mapToObj(i -> new Pair(sc.nextInt(), sc.nextInt())).collect(
            Collectors.toList());
        int[][][] dp = new int[N + 1][M + 1][K + 1];
        for (int i = 1; i <= N; i++) {
            Pair curOrder = orders.get(i - 1);
            int curX = curOrder.x;
            int curY = curOrder.y;

            for (int j = 1; j <= M; j++) {
                for (int k = 1; k <= K; k++) {
                    dp[i][j][k] = Math.max(dp[i][j - 1][k], Math.max(dp[i - 1][j][k], dp[i][j][k - 1]));
                    if (j - curX >= 0 && k - curY >= 0)
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - curX][k - curY] + 1);
                }
            }
        }

        System.out.println(dp[N][M][K]);
    }

    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

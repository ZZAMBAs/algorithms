package org.zzamba.p14855;

import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int n, m;
    static List<Mandu> mandus = new ArrayList<>();
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = inp[0];
        m = inp[1];
        mandus.add(new Mandu(n / inp[2], inp[2], inp[3]));
        dp = new int[m + 2][n + 1];

        for (int i = 0; i < m; i++) {
            inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            mandus.add(new Mandu(Math.min(inp[0] / inp[1], n / inp[2]), inp[2], inp[3]));
        }

        calDp();
        // printDp();

        System.out.print(dp[m + 1][n]);
    }

    static void calDp() {
        for (int r = 1; r <= m + 1; r++) {
            Mandu curMandu = mandus.get(r - 1);

            for (int c = n; c >= 0; c--) {
                if (c < curMandu.wheat || curMandu.maxCnt == 0)
                    dp[r][c] = dp[r - 1][c];
                else {
                    int cnt = 0;
                    int addVal = dp[r - 1][c - curMandu.wheat] + curMandu.cost;
                    int justVal = dp[r - 1][c];
                    dp[r][c] = Math.max(addVal, justVal);

                    if (justVal < addVal)
                        cnt++;

                    int next = c + curMandu.wheat;
                    int pre = c;
                    while (cnt < curMandu.maxCnt && next <= n) {
                        dp[r][next] = Math.max(dp[r][next], dp[r][pre] + curMandu.cost);

                        cnt++;
                        pre = next;
                        next += curMandu.wheat;
                    }
                }
            }
        }
    }

    static void printDp() {
        for (int r = 0; r < m + 2; r++) {
            for (int c = 0; c < n + 1; c++)
                System.out.print(dp[r][c] + " ");
            System.out.println();
        }
    }

    static class Mandu {
        int maxCnt;
        int wheat;
        int cost;

        public Mandu(int maxCnt, int wheat, int cost) {
            this.cost = cost;
            this.maxCnt = maxCnt;
            this.wheat = wheat;
        }
    }
}

package org.zzamba.p17351;

import java.util.*;

public class Main {
    static int N;
    static int[][] dp, map;
    static char[] str = { 'M', 'O', 'L', 'A' };
    static Map<Character, Integer> strToIdx;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        dp = new int[N + 1][N + 1];
        map = new int[N + 1][N + 1];
        strToIdx = new HashMap<>();
        for (int i = 0; i < str.length; i++)
            strToIdx.put(str[i], i);

        for (int i = 1; i <= N; i++) {
            String row = sc.next();
            for (int j = 1; j <= N; j++) {
                char c = row.charAt(j - 1);
                map[i][j] = strToIdx.getOrDefault(c, -1);
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int r1 = dp[i - 1][j] % str.length;
                int cmp1 = dp[i - 1][j] + (map[i][j] == r1 ? 1 : -r1);

                int r2 = dp[i][j - 1] % str.length;
                int cmp2 = dp[i][j - 1] + (map[i][j] == r2 ? 1 : -r2);

                dp[i][j] = Math.max(cmp1, cmp2);

                if (dp[i][j] % str.length == 0 && map[i][j] == 0)
                    dp[i][j]++;
            }
        }

        System.out.print(dp[N][N] / str.length);
    }
}

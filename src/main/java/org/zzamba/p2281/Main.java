package org.zzamba.p2281;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    static int n, m;
    static int[] names;
    static int[] preLength;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = inputs[0];
        m = inputs[1];
        preLength = new int[n + 1];
        names = IntStream.range(0, n).map(i -> {
			try {
                int name = Integer.parseInt(br.readLine());
                preLength[i + 1] = preLength[i] + name;
                return name;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}).toArray();
        dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        for (int i = 1; i <= n; i++) {
            int rest = m - preLength[i] - i + 1;

            if (rest < 0)
                break;

            dp[1][i] = rest * rest;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                for (int k = j + 1; k <= n; k++) {
                    if (dp[i][j] == Integer.MAX_VALUE)
                        break;

                    int rest = m - (preLength[k] - preLength[j]) - (k - j - 1);

                    if (rest < 0)
                        break;

                    if (k == n)
                        dp[i + 1][k] = dp[i][j];
                    else
                        dp[i + 1][k] = Math.min(dp[i + 1][k], dp[i][j] + rest * rest);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++)
            res = Math.min(res, dp[i][n]);

        System.out.print(res);
    }
}

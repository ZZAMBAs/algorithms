package org.zzamba.p2410;

import java.util.*;

public class Main {
    static final int MOD_NUM = 1_000_000_000;

    static int N;
    static int[] dp;
    static List<Integer> pow2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        dp = new int[N + 1];
        pow2 = new ArrayList<>();
        dp[0] = 1;
        for (int i = 1; i <= N; i *= 2)
            pow2.add(i);

        for (int pow : pow2)
            for (int i = pow; i <= N; i++)
                dp[i] = (dp[i] + dp[i - pow]) % MOD_NUM;

        System.out.print(dp[N]);
    }
}

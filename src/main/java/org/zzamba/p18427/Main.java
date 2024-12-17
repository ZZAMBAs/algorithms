package org.zzamba.p18427;

import java.util.*;
import java.util.stream.*;

public class Main {
    static final int MOD_NUM = 10_007;

    static int N, M, H;
    static int[] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] inputs = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inputs[0];
        M = inputs[1];
        H = inputs[2];
        dp = new int[H + 1];

        IntStream.range(0, N).forEach(i -> {
            List<Integer> blocks = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());

            cal(blocks);
        });

        System.out.print(dp[H] % MOD_NUM);
    }

    static void cal(List<Integer> blocks) {
        int temp[] = new int[H + 1];

        blocks.forEach(h -> {
            temp[h]++;
            for (int i = 0; i + h <= H; i++)
                temp[i + h] += dp[i];
        });

        for (int i = 0; i <= H; i++)
            dp[i] = (dp[i] + temp[i]) % MOD_NUM;
    }
}

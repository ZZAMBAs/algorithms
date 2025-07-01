package org.zzamba.p2631;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr = IntStream.range(0, sc.nextInt()).map(i -> sc.nextInt()).toArray();

        System.out.print(arr.length - findMax(arr));
    }

    public static int findMax(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = 1;

        IntStream.range(1, dp.length).forEach(i -> fdp(i, dp, arr));

        return Arrays.stream(dp).max().getAsInt();
    }

    public static int fdp(int idx, int[] dp, int[] arr) {
        if (dp[idx] > 0)
            return dp[idx];

        dp[idx] = IntStream.range(0, idx)
            .filter(i -> arr[i] < arr[idx])
            .map(i -> fdp(i, dp, arr) + 1)
            .max()
            .orElse(1);
        return dp[idx];
    }
}

package org.zzamba.p30460;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<Integer> A = IntStream.range(0, N).map(i -> sc.nextInt()).boxed().collect(Collectors.toList());
        long[][] dp = new long[3][N]; // [i][j] : 부스트 i 이면서 j 번째까지 최대 값.
        for (int i = 0; i < 3; i++)
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        dp[0][0] = A.get(0);
        dp[2][0] = A.get(0) * 2;

        for (int i = 1; i < N; i++) {
            dp[0][i] = Math.max(dp[0][i], Math.max(dp[0][i - 1] + A.get(i), dp[1][i - 1] + A.get(i) * 2));
            dp[1][i] = Math.max(dp[1][i], dp[2][i - 1] + A.get(i) * 2);
            dp[2][i] = Math.max(dp[2][i], dp[0][i - 1] + A.get(i) * 2);
        }

        System.out.println(Math.max(dp[0][N - 1], Math.max(dp[1][N - 1], dp[2][N - 1])));
    }
}

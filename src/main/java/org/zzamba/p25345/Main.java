package org.zzamba.p25345;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static final long MOD_NUM = 1_000_000_007L;
    static int N, K;
    static long[][] nCk; // [a][b]: aCb = (a-1)C(b-1) + (a-1)Cb

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        IntStream.range(0, N).forEach(i -> sc.nextInt());
        nCk = new long[N + 1][N + 1];
        nCk[1][1] = 1;

        System.out.print((comb(N, K) * pow2(K - 1) % MOD_NUM));
    }

    static long comb(int N, int K) {
        if (N < K || N <= 0)
            return 0L;

        if (K == 0 || N == K)
            return 1L;

        if (nCk[N][K] > 0)
            return nCk[N][K];

        nCk[N][K] = (comb(N - 1, K - 1) + comb(N - 1, K)) % MOD_NUM;
        return nCk[N][K];
    }

    static long pow2(int n) {
        long ret = 1;
        for (int i = 0; i < n; i++)
            ret = (ret * 2L) % MOD_NUM;
        return ret;
    }
}
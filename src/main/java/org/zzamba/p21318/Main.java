package org.zzamba.p21318;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, Q;
    static int[] diff, prefixSum;

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        diff = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        Q = sc.nextInt();
        prefixSum = new int[N]; // [i]: [0, i - 1]까지 실수 횟수
        IntStream.range(1, N).forEach(i -> {
            prefixSum[i] = prefixSum[i - 1];

            prefixSum[i] += diff[i - 1] > diff[i] ? 1 : 0;
        });
        IntStream.range(0, Q).forEach(i -> {
            int s = sc.nextInt();
            int e = sc.nextInt();

            sb.append(prefixSum[e - 1] - prefixSum[s - 1]).append('\n');
        });

        System.out.print(sb);
    }
}

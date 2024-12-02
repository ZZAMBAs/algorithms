package org.zzamba.p19951;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] heights = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        int[] dh = new int[N];
        IntStream.range(0, M).forEach(i -> {
            int s = sc.nextInt() - 1;
            int e = sc.nextInt();
            int k = sc.nextInt();

            dh[s] += k;

            if (e < N)
                dh[e] += -k;
        });

        int d = 0;

        for (int i = 0; i < N; i++) {
            d += dh[i];
            System.out.print((heights[i] + d) + " ");
        }
    }
}

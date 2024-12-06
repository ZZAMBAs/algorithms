package org.zzamba.p2831;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static final int CORRECT_VAL = 1500;
    static final int HEIGHT_LIMIT = 1001;

    static int N, res;
    static int[] menWantLong, menWantShort, womenWantLong, womenWantShort;

    public static void main(String[] args) {
        init();

        for (int i = 0; i < HEIGHT_LIMIT; i++) {
            find(i, menWantLong, womenWantShort);
            find(i, womenWantLong, menWantShort);
        }

        System.out.print(res);
    }

    static void find(int h, int[] start, int[] end) {
        for (int endIdx = h + 1; endIdx < HEIGHT_LIMIT && start[h] > 0; endIdx++) {
            int pairNum = Math.min(start[h], end[endIdx]);

            start[h] -= pairNum;
            end[endIdx] -= pairNum;
            res += pairNum;
        }
    }

    private static void init() {
        Scanner sc = new Scanner(System.in);
        res = 0;
        N = sc.nextInt();
        menWantLong = new int[HEIGHT_LIMIT];
        menWantShort = new int[HEIGHT_LIMIT];
        womenWantLong = new int[HEIGHT_LIMIT];
        womenWantShort = new int[HEIGHT_LIMIT];
        IntStream.range(0, N).forEach(i -> {
            int manH = sc.nextInt();

            if (manH < 0)
                menWantShort[Math.abs(manH) - CORRECT_VAL]++;
            else
                menWantLong[manH - CORRECT_VAL]++;
        });
        IntStream.range(0, N).forEach(i -> {
            int womenH = sc.nextInt();

            if (womenH < 0)
                womenWantShort[Math.abs(womenH) - CORRECT_VAL]++;
            else
                womenWantLong[womenH - CORRECT_VAL]++;
        });
    }
}

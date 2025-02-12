package org.zzamba.p2116;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int res, N;
    static int[] opponentIdx = { 5, 3, 4, 1, 2, 0 };
    static int[][] dices;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0;
        N = sc.nextInt();
        dices = new int[N][6];
        IntStream.range(0, N).forEach(i -> IntStream.range(0, 6).forEach(j -> dices[i][j] = sc.nextInt()));

        for (int i = 0; i < 6; i++)
            res = Math.max(bruteforce(0, i + 1, 0), res);

        System.out.println(res);
    }

    static int bruteforce(int idx, int curBottom, int sum) {
        if (idx == N)
            return sum;

        for (int i = 0; i < 6; i++) {
            int curV = dices[idx][i];

            if (curV != curBottom)
                continue;

            int opVal = dices[idx][opponentIdx[i]];

            if (curV != 6 && opVal != 6)
                return bruteforce(idx + 1, opVal, sum + 6);
            else if (curV == 5 || opVal == 5)
                return bruteforce(idx + 1, opVal, sum + 4);
            else
                return bruteforce(idx + 1, opVal, sum + 5);
        }

        return -1;
    }
}

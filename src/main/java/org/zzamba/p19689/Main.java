package org.zzamba.p19689;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int R, C, D, K, res;
    static String[] map;
    static int[][] prefixSum;

    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        D = sc.nextInt();
        K = sc.nextInt();
        res = 0;
        prefixSum =  new int[R + 2][C + 2];
        map = IntStream.range(0, R).mapToObj(i -> sc.next()).toArray(String[]::new);
        IntStream.rangeClosed(1, R)
            .forEach(i -> IntStream.rangeClosed(1, C)
                .forEach(j -> prefixSum[i][j] = prefixSum[i][j - 1] + prefixSum[i - 1][j] - prefixSum[i - 1][j - 1] + (map[i - 1].charAt(j - 1) == 'S' ? 1 : 0)));

        IntStream.range(0, R).forEach(i -> IntStream.range(0, C).forEach(j -> {
            if (map[i].charAt(j) == 'M')
                res += canHarvest(i + 1, j + 1) ? 1 : 0;
        }));

        System.out.print(res);
    }

    static boolean canHarvest(int r, int c) {
        int sprinklers = getPrefix(r + D, c + D) - getPrefix(r - D - 1, c + D) - getPrefix(r + D, c - D - 1) + getPrefix(r - D - 1, c - D - 1);

		return sprinklers >= K;
	}

    static int getPrefix(int r, int c) {
        int realR = Math.max(0, Math.min(r, R));
        int realC = Math.max(0, Math.min(c, C));

        return prefixSum[realR][realC];
    }

}

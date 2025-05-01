package org.zzamba.p25587;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int[] par, A, rain, childNum;
    static int res, N, M;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        par = IntStream.range(0, N + 1).map(i -> i).toArray();
        A = IntStream.range(0, N + 1).map(i -> {
            if (i == 0)
                return 0;

            return sc.nextInt();
        }).toArray();
        rain = IntStream.range(0, N + 1).map(i -> {
            if (i == 0)
                return 0;

            return sc.nextInt();
        }).toArray();
        childNum = new int[N + 1];
        Arrays.fill(childNum, 1);
        IntStream.range(1, N + 1).forEach(i -> {
            if (rain[i] > A[i]) res++;
        });

        IntStream.range(0, M).forEach(i -> {
            int cmd = sc.nextInt();

            if (cmd == 2)
                System.out.println(res);
            else
                merge(sc.nextInt(), sc.nextInt());
        });

    }

    static void merge(int i1, int i2) {
        int par1 = find(i1);
        int par2 = find(i2);

        if (par1 == par2)
            return;

        if (par1 > par2) {
            int temp = par1;
            par1 = par2;
            par2 = temp;
        }

        if (rain[par1] <= A[par1])
            res += childNum[par1];
        if (rain[par2] <= A[par2])
            res += childNum[par2];

        par[par2] = par1;
        A[par1] += A[par2];
        rain[par1] += rain[par2];
        childNum[par1] += childNum[par2];

        if (rain[par1] <= A[par1])
            res -= childNum[par1];
    }

    static int find(int idx) {
        if (par[idx] == idx)
            return idx;

        par[idx] = find(par[idx]);
        return par[idx];
    }
}

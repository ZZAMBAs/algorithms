package org.zzamba.p10888;

import java.io.*;
import java.util.stream.IntStream;

public class Main {
    static int N;
    static long pairNum, sumOfBridges;
    static int[] par;
    static long[] childrenCnt;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        par = IntStream.range(0, N + 1).toArray();
        childrenCnt = IntStream.range(0, N + 1).mapToLong(i -> 1L).toArray();

        for (int i = 0; i < N - 1; i++) {
            cal(Integer.parseInt(br.readLine()));

            sb.append(pairNum).append(' ').append(sumOfBridges).append('\n');
        }

        System.out.print(sb);
    }

    static void cal(int i) {
        pairNum += childrenCnt[find(i)] * childrenCnt[find(i + 1)];
        sumOfBridges -= getPathCnt(i) + getPathCnt(i + 1);
        merge(i, i + 1);
        sumOfBridges += getPathCnt(i);
    }

    static long getPathCnt(int i) {
        long children = childrenCnt[find(i)];
        return children * children * (children - 1L) / 2L - children * (children - 1) * (2L * children - 1L) / 6L;
    }

    static int find(int idx) {
        if (par[idx] == idx)
            return par[idx];

        par[idx] = find(par[idx]);
        return par[idx];
    }

    static void merge(int idx1, int idx2) {
        int par1 = find(idx1);
        int par2 = find(idx2);

        if (par1 == par2)
            return;

        childrenCnt[par1] += childrenCnt[par2];
        par[par2] = par1;
    }
}

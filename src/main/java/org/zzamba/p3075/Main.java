package org.zzamba.p3075;

import java.util.*;
import java.io.*;

public class Main {
    static final int INF = 10001;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int T, n, p, q;
    static int[] meeting;
    static int[][] dist;

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            init();
            floydWarshall();
            long[] astroInfo = getMinAstro(); // 가장 가까운 은하, 비용
            sb.append(astroInfo[0]).append(" ").append(astroInfo[1]).append("\n");
        }

        System.out.print(sb);
    }

    static void init() throws Exception {
        int[] initCmd = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = initCmd[0];
        p = initCmd[1];
        q = initCmd[2];
        meeting = new int[101];
        dist = new int[p + 1][p + 1];
        for (int i = 0; i < p + 1; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for (int i = 0; i < n; i++)
            meeting[Integer.parseInt(br.readLine())]++;
        for (int i = 0; i < q; i++) {
            int[] qCmd = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            dist[qCmd[0]][qCmd[1]] = Math.min(dist[qCmd[0]][qCmd[1]], qCmd[2]);
            dist[qCmd[1]][qCmd[0]] = Math.min(dist[qCmd[1]][qCmd[0]], qCmd[2]);
        }
    }

    static void floydWarshall() {
        for (int k = 1; k < p + 1; k++)
            for (int i = 1; i < p + 1; i++)
                for (int j = 1; j < p + 1; j++)
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
    }

    static long[] getMinAstro() {
        long retFee = 100L * INF * INF;
        long retAstro = 0L;

        for (int i = 1; i < p + 1; i++) {
            long tempFee = 0L;

            for (int astro = 1; astro < p + 1; astro++) {
                tempFee += (long)meeting[astro] * dist[i][astro] * dist[i][astro];
            }

            if (tempFee < retFee) {
                retFee = tempFee;
                retAstro = i;
            }
        }

        return new long[]{retAstro, retFee};
    }
}

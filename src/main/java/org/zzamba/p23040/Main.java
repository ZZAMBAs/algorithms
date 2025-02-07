package org.zzamba.p23040;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N;
    static long res;
    static List<List<Integer>> adj;
    static int[] par;
    static long[] cnt;
    static String colors;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0L;
        N = sc.nextInt();
        adj = new ArrayList<>();
        visited = new boolean[N];
        par = IntStream.range(0, N).toArray();
        IntStream.range(0, N).forEach(i -> adj.add(new ArrayList<>()));
        cnt = new long[N];
        IntStream.range(0, N - 1).forEach(i -> {
            int v1 = sc.nextInt() - 1;
            int v2 = sc.nextInt() - 1;

            adj.get(v1).add(v2);
            adj.get(v2).add(v1);
        });
        colors = sc.next();

        for (int i = 0; i < N; i++)
            if (colors.charAt(i) == 'R' && !visited[i])
                bfsRed(i);

        for (int i = 0; i < N; i++)
            if (colors.charAt(i) == 'B')
                for (int adv : adj.get(i))
                    res += cnt[find(adv)];

        System.out.print(res);
    }

    static void bfsRed(int start) {
        visited[start] = true;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        cnt[start] = 1L;
        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int curV = q.poll();

                for (int adv : adj.get(curV)) {
                    if (colors.charAt(adv) == 'B' || visited[adv])
                        continue;

                    cnt[adv]++;
                    visited[adv] = true;
                    union(curV, adv);
                    q.add(adv);
                }

            }
        }

    }

    static int find(int idx) {
        if (par[idx] == idx)
            return par[idx];

        par[idx] = find(par[idx]);
        return par[idx];
    }

    static void union(int idx1, int idx2) {
        int par1 = find(idx1);
        int par2 = find(idx2);

        if (par1 == par2)
            return;

        par[par2] = par1;
        cnt[par1] += cnt[par2];
        cnt[par2] = 0L;
    }
}

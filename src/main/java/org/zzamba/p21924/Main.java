package org.zzamba.p21924;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N, M;
    static long res;

    static List<Triple<Integer, Integer, Long>> edges;
    static int[] par;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        res = 0;
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inp[0];
        M = inp[1];
        edges = new ArrayList<>();
        par = IntStream.range(0, N).toArray();
        IntStream.range(0, M).forEach(i -> {
            try {
                String[] input = br.readLine().split(" ");
                int a = Integer.parseInt(input[0]) - 1;
                int b = Integer.parseInt(input[1]) - 1;
                long w = Long.parseLong(input[2]);
                res += w;
                edges.add(new Triple<>(a, b, w));
            } catch (Exception e) {}
        });

        res -= kruskal();
        System.out.println(res >= 0 ? res : -1);
    }

    static long kruskal() {
        long ret = 0;
        edges.sort(Comparator.comparing(Triple::getU));
        for (Triple<Integer, Integer, Long> edge : edges) {
            if (find(edge.t) == find(edge.r))
                continue;

            union(edge.t, edge.r);
            ret += edge.u;
        }

        int curPar = find(0);
        boolean allVisited = true;
        for (int i = 1; i < N; i++)
            if (curPar != find(i))
                allVisited = false;

        return allVisited ? ret : -Long.MAX_VALUE;
    }

    static int find(int idx) {
        if (par[idx] == idx)
            return par[idx];

        par[idx] = find(par[idx]);
        return par[idx];
    }

    static void union(int a, int b) {
        int parA = find(a);
        int parB = find(b);

        if (parA == parB)
            return;

        par[parB] = parA;
    }

    static class Triple<T, R, U> {
        T t;
        R r;
        U u;

        public Triple(T t, R r, U u) {
            this.t = t;
            this.r = r;
            this.u = u;
        }

        public U getU() {
            return u;
        }
    }
}

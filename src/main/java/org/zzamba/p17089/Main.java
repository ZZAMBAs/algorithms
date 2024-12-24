package org.zzamba.p17089;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N, M, res;
    static List<List<Integer>> adj;
    static boolean[][] edges;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = Integer.MAX_VALUE;
        N = sc.nextInt();
        M = sc.nextInt();
        visited = new boolean[N + 1];
        edges = new boolean[N + 1][N + 1];
        adj = new ArrayList<>();
        IntStream.range(0, N + 1).forEach(i -> adj.add(new ArrayList<>()));
        IntStream.range(0, M).forEach(i -> {
            int a = sc.nextInt();
            int b = sc.nextInt();

            adj.get(a).add(b);
            adj.get(b).add(a);
            edges[a][b] = true;
            edges[b][a] = true;
        });

        for (int first = 0; first < N; first++) {
            for (int second : adj.get(first)) {
                for (int third : adj.get(second)) {
                    if (!edges[first][third])
                        continue;

                    res = Math.min(res, adj.get(first).size() + adj.get(second).size() + adj.get(third).size() - 6);
                }
            }
        }

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }

}

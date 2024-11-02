package org.zzamba.p1277;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int N, W;
    static double M;
    static double[][] edges; // 거리
    static List<Pair<Long, Long>> coor;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        W = sc.nextInt();
        M = sc.nextDouble();
        edges = new double[N][N];
        for (int i = 0; i < N; i++)
            Arrays.fill(edges[i], Double.MAX_VALUE);
        coor = IntStream.range(0, N)
            .mapToObj(i -> new Pair<>(sc.nextLong(), sc.nextLong()))
            .collect(Collectors.toList());
        for (int i = 0; i < N - 1; i++) {
            Pair<Long, Long> coor1 = coor.get(i);
            for (int j = i + 1; j < N; j++) {
                Pair<Long, Long> coor2 = coor.get(j);
                long dist2x = (coor1.t - coor2.t) * (coor1.t - coor2.t) + (coor1.r - coor2.r) * (coor1.r - coor2.r);

                if (dist2x <= M * M) {
                    edges[i][j] = edges[j][i] = Math.sqrt(dist2x);
                }
            }
        }
        IntStream.range(0, W).forEach(i -> {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            edges[a][b] = edges[b][a] = .0d;
        });

        System.out.print(dijkstra());
    }

    static long dijkstra() {
        double[] visited = new double[N];
        Arrays.fill(visited, Double.MAX_VALUE);
        PriorityQueue<Pair<Double, Integer>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getT)); // { 총 거리, 현 위치 }
        pq.add(new Pair<>(.0d, 0));
        visited[0] = .0d;

        while (!pq.isEmpty()) {
            Pair<Double, Integer> p = pq.poll();
            double dist = p.t;
            int loc = p.r;

            if (loc != 0 && visited[loc] < dist)
                continue;

            if (loc == N - 1)
                return (long) (dist * 1000.0d);

            for (int i = 0; i < N; i++) {
                if (i == loc)
                    continue;

                double nextDist = dist + edges[loc][i];

                if (nextDist < visited[i]) {
                    visited[i] = nextDist;
                    pq.add(new Pair<>(nextDist, i));
                }
            }
        }

        return -1L;
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        public T getT() {
            return t;
        }
    }
}

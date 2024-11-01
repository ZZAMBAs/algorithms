package org.zzamba.p17396;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, M;
    static long res;
    static boolean[] light;
    static List<List<Pair<Integer, Integer>>> adv; // {시간, 목적지}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = Long.MAX_VALUE;
        N = sc.nextInt();
        M = sc.nextInt();
        light = new boolean[N];
        adv = new ArrayList<>();

        IntStream.range(0, N).forEach(i -> {
            adv.add(new ArrayList<>());
            light[i] = sc.nextInt() == 1;
        });
        light[N - 1] = false;
        IntStream.range(0, M).forEach(i -> {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int t = sc.nextInt();

            if (!light[a] && !light[b]) {
                adv.get(a).add(new Pair<>(t, b));
                adv.get(b).add(new Pair<>(t, a));
            }

        });

        dijkstra();

        System.out.print(res == Long.MAX_VALUE ? -1 : res);
    }

    static void dijkstra() {
        long[] visited = new long[N];
        Arrays.fill(visited, Long.MAX_VALUE);
        PriorityQueue<Pair<Long, Integer>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getX));
        pq.add(new Pair<>(0L, 0)); // {총 거리, 현 위치}
        visited[0] = 0;

        while (!pq.isEmpty()) {
            Pair<Long, Integer> curP = pq.poll();
            long curDist = curP.x;
            int curLoc = curP.y;

            if (visited[curLoc] < curDist)
                continue;

            if (curLoc == N - 1) {
                res = curDist;
                return;
            }

            for (Pair<Integer, Integer> next : adv.get(curLoc)) {
                int nextLoc = next.y;
                long nextDist = curDist + next.x;

                if (visited[nextLoc] > nextDist) {
                    visited[nextLoc] = nextDist;
                    pq.add(new Pair<>(nextDist, nextLoc));
                }
            }
        }
    }

    static class Pair<T, R> {
        T x;
        R y;

        public Pair(T x, R y) {
            this.x = x;
            this.y = y;
        }

        public T getX() {
            return x;
        }
    }
}

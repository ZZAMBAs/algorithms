package org.zzamba.p1719;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int[][] nextVertex;
    static int n, m;
    static List<List<Pair<Integer, Integer>>> adv; // {목적지, 가중치}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        adv = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> adv.add(new ArrayList<>()));
        nextVertex = new int[n][n];
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;
            int w = sc.nextInt();

            adv.get(a).add(new Pair<>(b, w));
            adv.get(b).add(new Pair<>(a, w));
        }
        for (int i = 0; i < n; i++)
            Arrays.fill(nextVertex[i], -1);

        IntStream.range(0, n).forEach(i -> dijkstra(i));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    System.out.print("- ");
                else
                    System.out.print(nextVertex[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void dijkstra(int v) {
        PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getT));
        pq.add(new Pair<>(0, new Pair<>(v, null))); // {전체 거리, {현 위치, 첫 방문 위치} }
        int[] visited = new int[n];
        Arrays.fill(visited, Integer.MAX_VALUE);
        visited[v] = 0;

        while (!pq.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> curP = pq.poll();
            int curDist = curP.t;
            int curLoc = curP.r.t;
            Integer firstVertex = curP.r.r;

            if (curLoc != v && visited[curLoc] < curDist)
                continue;

            if (curLoc != v && firstVertex == null)
                firstVertex = curLoc;

            nextVertex[v][curLoc] = firstVertex == null ? -1 : firstVertex + 1;

            for (Pair<Integer, Integer> next : adv.get(curLoc)) {
                int nextLoc = next.t;
                int nextDist = curDist + next.r;

                if (visited[nextLoc] > nextDist) {
                    visited[nextLoc] = nextDist;
                    pq.add(new Pair<>(nextDist, new Pair<>(nextLoc, firstVertex)));
                }
            }
        }

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

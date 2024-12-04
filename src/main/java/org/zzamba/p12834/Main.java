package org.zzamba.p12834;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N, V, E, A, B, res;
    static int[] homeNum;
    static List<List<Pair<Integer, Integer>>> adv; // { dest, weight }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        adv = new ArrayList<>();
        N = sc.nextInt();
        V = sc.nextInt();
        E = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        homeNum = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        IntStream.rangeClosed(0, V).forEach(i -> adv.add(new ArrayList<>()));
        IntStream.range(0, E).forEach(i -> {
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();
            int w = sc.nextInt();

            adv.get(n1).add(new Pair<>(n2, w));
            adv.get(n2).add(new Pair<>(n1, w));
        });

        Arrays.stream(homeNum).forEach(home -> {
            res += dijkstra(home);
        });

        System.out.print(res);
    }

    static int dijkstra(int home) {
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getR)); // { cur, distSum }
        pq.add(new Pair<>(home, 0));
        int[] dist = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[home] = 0;

        while (!pq.isEmpty()) {
            Pair<Integer, Integer> top = pq.poll();
            int cur = top.t;
            int distSum = top.r;

            for (Pair<Integer, Integer> adj : adv.get(cur)) {
                int next = adj.t;
                int nextDistSum = distSum + adj.r;

                if (nextDistSum < dist[next]) {
                    pq.add(new Pair<>(next, nextDistSum));
                    dist[next] = nextDistSum;
                }
            }

        }

        return (dist[A] == Integer.MAX_VALUE ? -1 : dist[A]) + (dist[B] == Integer.MAX_VALUE ? -1 : dist[B]);
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        public R getR() {
            return r;
        }
    }
}

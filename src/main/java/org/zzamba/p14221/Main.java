package org.zzamba.p14221;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int n, m, p, q;
    static List<List<Pair<Integer, Integer>>> adv; // { 이웃벡터, 가중치 }
    static boolean[] isConvenience, isHouseCand;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        adv = new ArrayList<>();
        IntStream.rangeClosed(0, n).forEach(i -> adv.add(new ArrayList<>()));
        isConvenience = new boolean[n + 1];
        isHouseCand = new boolean[n + 1];
        IntStream.range(0, m).forEach(i -> {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            int w = sc.nextInt();

            adv.get(v1).add(new Pair<>(v2, w));
            adv.get(v2).add(new Pair<>(v1, w));
        });
        p = sc.nextInt();
        q = sc.nextInt();
        IntStream.range(0, p).forEach(i -> isHouseCand[sc.nextInt()] = true);
        IntStream.range(0, q).forEach(i -> isConvenience[sc.nextInt()] = true);

        System.out.print(allDijkstra());
    }

    static int allDijkstra() {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Pair<Pair<Integer, Integer>, Integer>> pq = new PriorityQueue<>(Comparator.<Pair<Pair<Integer, Integer>, Integer>, Integer>comparing(pair -> pair.t.r).thenComparing(Pair::getR)); // { { 현 위치, 거리 }, 시작점 }
        IntStream.rangeClosed(1, n).forEach(i -> {
            if (isHouseCand[i]) {
                pq.add(new Pair<>(new Pair<>(i, 0), i));
                dist[i] = 0;
            }
        });

        while (!pq.isEmpty()) {
            Pair<Pair<Integer, Integer>, Integer> top = pq.poll();

            if (!isHouseCand[top.t.t] && top.t.r > dist[top.t.t])
                continue;

            if (isConvenience[top.t.t])
                return top.r;

            for (Pair<Integer, Integer> pair : adv.get(top.t.t)) {
                int nextDest = pair.t;
                int nextW = top.t.r + pair.r;

                if (dist[nextDest] > nextW) {
                    dist[nextDest] = nextW;
                    pq.add(new Pair<>(new Pair<>(nextDest, nextW), top.r));
                }
            }
        }

        return -1;
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

        public R getR() {
            return r;
        }
    }
}

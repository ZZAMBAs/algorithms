package org.zzamba.p1595;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static List<List<Pair<Integer, Long>>> adj; // { 이웃 위치, 가중치 }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        adj = new ArrayList<>();
        IntStream.range(0, 10001).forEach(i -> adj.add(new ArrayList<>()));

        while (sc.hasNext()) {
            long[] inputs = Arrays.stream(sc.nextLine().split(" ")).mapToLong(Long::parseLong).toArray();
            adj.get((int)inputs[0]).add(new Pair<>((int)inputs[1], inputs[2]));
            adj.get((int)inputs[1]).add(new Pair<>((int)inputs[0], inputs[2]));
        }

        Pair<Integer, Long> side1 = bfs(1); // { 가장 먼 위치, 거리 }
        System.out.println(bfs(side1.t).r);
    }

    static Pair<Integer, Long> bfs(int start) {
        Queue<Pair<Integer, Long>> q = new ArrayDeque<>();
        q.add(new Pair<>(start, 0L));
        long[] dist = new long[10001];

        while (!q.isEmpty()) {
            Pair<Integer, Long> front = q.poll();

            adj.get(front.t).forEach(p -> {
                if (dist[p.t] == 0L) {
                    dist[p.t] = front.r + p.r;
                    q.add(new Pair<>(p.t, dist[p.t]));
                }
            });
        }

        int maxIdx = 0;
        long maxV = -1;
        for (int i = 10000; i > 0; i--) {
            if (dist[i] > maxV) {
                maxV = dist[i];
                maxIdx = i;
            }
        }

        return new Pair<>(maxIdx, maxV);
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }
    }
}

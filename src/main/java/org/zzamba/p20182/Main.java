package org.zzamba.p20182;

import java.util.*;
import java.util.stream.*;

public class Main {
    private static final int C_LIMIT = 20;

    static int N, M, A, B, C, res;
    static List<List<Pair>> adj; // {교차로, { 교차로와 연결된 골목, 가중치 } }
    static int[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        C = sc.nextInt();
        res = Integer.MAX_VALUE;
        adj = new ArrayList<>();
        IntStream.rangeClosed(0, N).forEach(i -> adj.add(new ArrayList<>()));
        IntStream.range(0, M).forEach(i -> {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int w = sc.nextInt();

            adj.get(a).add(new Pair(b, w));
            adj.get(b).add(new Pair(a, w));
        });

        for (int i = 1; i <= C_LIMIT && res == Integer.MAX_VALUE; i++)
            dijkstra(i);

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }

    static void dijkstra(int limit) {
        visited = new int[N + 1]; // 총 금액
        Arrays.fill(visited, Integer.MAX_VALUE);
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparing(Pair::getA));
        pq.add(new Pair(0, A)); // { 가진 금액, 현 위치 }
        visited[A] = 0;
        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int curMoney = p.a;
            int curLoc = p.b;

            if (curLoc != A && visited[curLoc] < curMoney)
                continue;

            if (curLoc == B) {
                if (curMoney <= C)
                    res = limit;
                return;
            }

            for (Pair nextP : adj.get(curLoc)) {
                if (nextP.b > limit)
                    continue;

                int nextLoc = nextP.a;
                int nextMoney = curMoney + nextP.b;

                if (nextMoney < visited[nextLoc]) {
                    pq.add(new Pair(nextMoney, nextLoc));
                    visited[nextLoc] = nextMoney;
                }
            }
        }

    }

    static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }
    }
}

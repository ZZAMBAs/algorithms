package org.zzamba.p11562;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int n, m, u, v, b, k;
    static int[][] visited;
    static List<List<Pair>> adj; // {방향, 가중치}, 가중치는 outDeg이면 0, inDeg면 1.

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        adj = new ArrayList<>();
        n = sc.nextInt();
        m = sc.nextInt();
        visited = new int[n + 1][n + 1]; // [시작점][끝점]: 시작점에서 끝점까지 바꿔야 하는 최소 횟수
        IntStream.rangeClosed(0, n).forEach(i -> adj.add(new ArrayList<>()));
        IntStream.range(0, m).forEach(i -> {
            u = sc.nextInt();
            v = sc.nextInt();
            b = sc.nextInt();

            adj.get(u).add(new Pair(v, 0));
            if (b == 1)
                adj.get(v).add(new Pair(u, 0));
            else
                adj.get(v).add(new Pair(u, 1));
        });

        k = sc.nextInt();
        IntStream.rangeClosed(1, n).forEach(Main::dijkstra);

        System.out.print(IntStream.range(0, k)
            .mapToObj(i -> String.valueOf(visited[sc.nextInt()][sc.nextInt()]))
            .collect(Collectors.joining("\n")));
    }

    static int dijkstra(int s) {
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparing(Pair::getY));
        Arrays.fill(visited[s], Integer.MAX_VALUE);
        visited[s][s] = 0;
        pq.add(new Pair(s, 0)); // { 현 위치, 현재까지의 바꿀 도로 개수 합 }

        while (!pq.isEmpty()) {
            Pair top = pq.poll();
            int curLoc = top.x;
            int curWSum = top.y;

            if (curLoc != s && visited[s][curLoc] < curWSum)
                continue;

            for (Pair adv : adj.get(curLoc)) {
                int nextLoc = adv.x;
                int nextWSum = curWSum + adv.y;

                if (visited[s][nextLoc] > nextWSum) {
                    visited[s][nextLoc] = nextWSum;
                    pq.add(new Pair(nextLoc, nextWSum));
                }
            }
        }

        return -1;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getY() {
            return y;
        }
    }
}

package org.zzamba.p20168;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, M, A, B, C, res;
    static List<List<Pair>> adj; // {교차로, { 교차로와 연결된 골목, 가중치 } }
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        A = sc.nextInt();
        B = sc.nextInt();
        C = sc.nextInt();
        res = Integer.MAX_VALUE;
        adj = new ArrayList<>();
        visited = new boolean[N + 1];
        IntStream.rangeClosed(0, N).forEach(i -> adj.add(new ArrayList<>()));
        IntStream.range(0, M).forEach(i -> {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int w = sc.nextInt();

            adj.get(a).add(new Pair(b, w));
            adj.get(b).add(new Pair(a, w));
        });

        visited[A] = true;
        findPath(A, 0, 0);

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }

    static void findPath(int idx, int sum, int max) {
        if (idx == B) {
            if (sum <= C)
                res = Math.min(res, max);
            return;
        }

        for (Pair adv : adj.get(idx)) {
            if (!visited[adv.x]) {
                visited[adv.x] = true;
                findPath(adv.x, sum + adv.y, Math.max(max, adv.y));
                visited[adv.x] = false;
            }
        }
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

package org.zzamba.p30508;

import java.util.*;

public class Main {
    static int N, M, K, h, w, res;
    static int[][] heights;
    static List<Pair<Integer, Integer>> holes;
    static boolean[][] safePoints;
    static int[][] dl = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0;
        N = sc.nextInt();
        M = sc.nextInt();
        h = sc.nextInt();
        w = sc.nextInt();
        heights = new int[N][M];
        safePoints = new boolean[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                heights[i][j] = sc.nextInt();
        holes = new ArrayList<>();
        K = sc.nextInt();
        for (int i = 0; i < K; i++)
            holes.add(new Pair<>(sc.nextInt() - 1, sc.nextInt() - 1));
        updateSafePoints();
        count();

        System.out.println(res);
    }

    static void updateSafePoints() {
        holes.forEach(hole -> {
            if (!safePoints[hole.t][hole.r]) {
                Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
                safePoints[hole.t][hole.r] = true;
                q.add(new Pair<>(hole.t, hole.r));

                while (!q.isEmpty()) {
                    Pair<Integer, Integer> p = q.poll();

                    for (int[] d : dl) {
                        int nextR = p.t + d[0];
                        int nextC = p.r + d[1];
                        if (nextR >= 0 && nextR < N && nextC >= 0 && nextC < M && !safePoints[nextR][nextC] && heights[nextR][nextC] >= heights[p.t][p.r]) {
                            safePoints[nextR][nextC] = true;
                            q.add(new Pair<>(nextR, nextC));
                        }
                    }
                }
            }
        });
    }

    static void count() {
        int[][] prefixSum = new int[N][M];
        prefixSum[0][0] = safePoints[0][0] ? 0 : 1;
        for (int i = 1; i < N; i++)
            prefixSum[i][0] = prefixSum[i - 1][0] + (safePoints[i][0] ? 0 : 1);
        for (int i = 1; i < M; i++)
            prefixSum[0][i] = prefixSum[0][i - 1] + (safePoints[0][i] ? 0 : 1);

        for (int i = 1; i < N; i++)
            for (int j = 1; j < M; j++)
                prefixSum[i][j] = prefixSum[i - 1][j] + prefixSum[i][j - 1] - prefixSum[i - 1][j - 1] + (safePoints[i][j] ? 0 : 1);

        for (int i = 0; i <= N - h; i++)
            for (int j = 0; j <= M - w; j++) {
                int val = prefixSum[i + h - 1][j + w - 1];

                if (i - 1 >= 0)
                    val -= prefixSum[i - 1][j + w - 1];

                if (j - 1 >= 0)
                    val -= prefixSum[i + h - 1][j - 1];

                if (i - 1 >= 0 && j - 1 >= 0)
                    val += prefixSum[i - 1][j - 1];

                if (val == 0)
                    res++;
            }

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

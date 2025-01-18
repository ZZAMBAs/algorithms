package org.zzamba.p17836;

import java.util.*;

public class Main {
    static final int WALL = 1;
    static final int GRAM = 2;
    static final int[][] dl = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

    static int N, M, T;
    static int[][] map;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        T = sc.nextInt();
        map = new int[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                map[i][j] = sc.nextInt();

        int res = cal();
        System.out.print(res == Integer.MAX_VALUE || res > T ? "Fail" : res);
    }

    static int cal() {
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(new Pair<>(0, 0));
        int ret = Integer.MAX_VALUE;
        boolean[][] isVisited = new boolean[N][M];
        isVisited[0][0] = true;

        int curTime = 0;
        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                Pair<Integer, Integer> front = q.poll();

                if (map[front.t][front.r] == GRAM) {
                    ret = Math.min(ret, curTime + (N - 1) - front.t + (M - 1) - front.r);
                    continue;
                }

                if (front.t == N - 1 && front.r == M - 1) {
                    ret = Math.min(ret, curTime);
                    continue;
                }

                for (int[] d : dl) {
                    int nextR = front.t + d[0];
                    int nextC = front.r + d[1];

                    if (inRange(nextR, nextC) && !isVisited[nextR][nextC] && map[nextR][nextC] != WALL) {
                        q.add(new Pair<>(nextR, nextC));
                        isVisited[nextR][nextC] = true;
                    }
                }
            }

            curTime++;
        }

        return ret;
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
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

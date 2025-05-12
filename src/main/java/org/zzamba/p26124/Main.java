package org.zzamba.p26124;

import java.util.*;

public class Main {
    static int H, W, res = -1;
    static int[][] map, dl = new int[][]{ {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    static boolean[][] bfsVisited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        W = sc.nextInt();
        map = new int[H][W];
        bfsVisited = new boolean[H][W];
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
                map[i][j] = sc.nextInt();

                if (map[i][j] <= 0)
                    bfsVisited[i][j] = true;
            }

        if (validate())
            getRes();

        System.out.print(res);
    }

    static boolean validate() {
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
                if (map[i][j] == -1)
                    continue;

                for (int[] d : dl) {
                    int nextR = i + d[0];
                    int nextC = j + d[1];
                    if (inRange(nextR, nextC) && map[nextR][nextC] != -1 && Math.abs(map[nextR][nextC] - map[i][j]) > 1)
                        return false;
                }
            }

        return true;
    }

    static void getRes() {
        res = 0;

        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++) {
                if (bfsVisited[i][j])
                    continue;

                boolean sw = true;
                for (int[] d : dl) {
                    int nextR = i + d[0];
                    int nextC = j + d[1];

					if (inRange(nextR, nextC) && map[nextR][nextC] > map[i][j]) {
						sw = false;
						break;
					}
                }

                if (sw) {
                    bfs(i, j);
                    res++;
                }
            }
    }

    static void bfs(int r, int c) {
        Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
        q.add(new Pair<>(r, c));
        bfsVisited[r][c] = true;

        while (!q.isEmpty()) {
            Pair<Integer, Integer> front = q.poll();

            for (int[] d : dl) {
                int nextR = r + d[0];
                int nextC = c + d[1];

                if (inRange(nextR, nextC) && !bfsVisited[nextR][nextC] && map[front.t][front.r] - map[nextR][nextC] == 1) {
                    q.add(new Pair<>(nextR, nextC));
                    bfsVisited[nextR][nextC] = true;
                }
            }
        }
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < H && c >= 0 && c < W;
    }

    static class Pair<T, R> {
        T t;
        R r;
        Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }
    }

}

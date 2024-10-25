package org.zzamba.p23747;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int R, C, Hr, Hc;
    static int[][] dl = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} }; // UDLR
    static String path;
    static List<String> map;
    static List<Pair> wards;
    static boolean[][] canSee;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        map = IntStream.range(0, R).mapToObj(i -> sc.next()).collect(Collectors.toList());
        Hr = sc.nextInt() - 1;
        Hc = sc.nextInt() - 1;
        path = sc.next();
        canSee = new boolean[R][C];
        wards = new ArrayList<>();

        walk();
        bfsWards();
        lightAround();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (canSee[i][j])
                    sb.append('.');
                else
                    sb.append('#');
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }

    static void walk() {
        for (char c : path.toCharArray()) {
            Hr += switch (c) {
                case 'U' -> dl[0][0];
                case 'D' -> dl[1][0];
                case 'L' -> dl[2][0];
                case 'R' -> dl[3][0];
                default -> 0;
            };

            Hc += switch (c) {
                case 'U' -> dl[0][1];
                case 'D' -> dl[1][1];
                case 'L' -> dl[2][1];
                case 'R' -> dl[3][1];
                default -> 0;
            };

            if (c == 'W')
                wards.add(new Pair(Hr, Hc));
        }
    }

    static void bfsWards() {
        for (Pair p : wards) {
            if (canSee[p.x][p.y])
                continue;

            bfs(p, map.get(p.x).charAt(p.y));
        }
    }

    static void lightAround() {
        canSee[Hr][Hc] = true;
        for (int[] d : dl) {
            int nextR = Hr + d[0];
            int nextC = Hc + d[1];

            if (inRange(nextR, nextC))
                canSee[nextR][nextC] = true;
        }
    }

    static void bfs(Pair p, char alpha) {
        Queue<Pair> q = new ArrayDeque<>();
        q.add(p);
        canSee[p.x][p.y] = true;

        while (!q.isEmpty()) {
            Pair curP = q.poll();

            for (int[] d : dl) {
                int nextR = curP.x + d[0];
                int nextC = curP.y + d[1];

                if (inRange(nextR, nextC) && !canSee[nextR][nextC] && map.get(nextR).charAt(nextC) == alpha) {
                    canSee[nextR][nextC] = true;
                    q.add(new Pair(nextR, nextC));
                }
            }
        }
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

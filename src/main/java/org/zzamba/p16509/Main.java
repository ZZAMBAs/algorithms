package org.zzamba.p16509;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static final int BOARD_LEN = 10;

    static int R1, C1, R2, C2, res;
    static int[][][] dl = {
        { {0, 1}, {1, 2}, {2, 3} }, { {0, 1}, {-1, 2}, {-2, 3} },
        { {0, -1}, {-1, -2}, {-2, -3} }, { {0, -1}, {1, -2}, {2, -3} },
        { {1, 0}, {2, 1}, {3, 2} }, { {1, 0}, {2, -1}, {3, -2} },
        { {-1, 0}, {-2, 1}, {-3, 2} }, { {-1, 0}, {-2, -1}, {-3, -2} },
    };
    static boolean[][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        visited = new boolean[BOARD_LEN][BOARD_LEN - 1];
        res = 0;
        R1 = sc.nextInt(); C1 = sc.nextInt(); R2 = sc.nextInt(); C2 = sc.nextInt();

        res = bfs();

        System.out.println(res);
    }

    static int bfs() {
        int level = 0;
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(R1, C1));
        visited[R1][C1] = true;
        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                Pair p = q.poll();
                int curX = p.x;
                int curY = p.y;

                if (curX == R2 && curY == C2)
                    return level;

                for (int[][] d : dl) {
                    boolean sw = true;
                    Pair[] next = Arrays.stream(d).map(delta -> new Pair(curX + delta[0], curY + delta[1])).toArray(Pair[]::new);

                    for (int j = 0; j < next.length; j++) {
                        Pair nextP = next[j];
                        if (!inRange(nextP)) {
                            sw = false;
                            break;
                        }

                        if (j != next.length - 1 && (nextP.x == R2 && nextP.y == C2)) {
                            sw = false;
                            break;
                        }

                        if (j == next.length - 1 && visited[nextP.x][nextP.y]) {
                            sw = false;
                            break;
                        }
                    }

                    if (sw) {
                        visited[curX][curY] = true;
                        q.add(next[next.length - 1]);
                    }
                }

            }
            level++;
        }


        return -1;
    }

    static boolean inRange(Pair p) {
        return p.x >= 0 && p.x < BOARD_LEN && p.y >= 0 && p.y < BOARD_LEN - 1;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

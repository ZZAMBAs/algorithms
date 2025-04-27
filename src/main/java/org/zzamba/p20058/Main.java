package org.zzamba.p20058;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, Q, cells;
    static List<Integer> magics;
    static int[][] ice;
    static int[][] dl = new int[][]{ {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    static boolean[][] isVisited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Q = sc.nextInt();

        cells = 1;
        for (int i = 0; i < N; i++)
            cells *= 2;

        isVisited = new boolean[cells][cells];
        ice = new int[cells][cells];
        for (int i = 0; i < cells; i++)
            for (int j = 0; j < cells; j++)
                ice[i][j] = sc.nextInt();
        magics = IntStream.range(0, Q).map(i -> sc.nextInt()).boxed().collect(Collectors.toList());

        for (int q : magics) {
            int div = 1;
            for (int i = 0; i < q; i++)
                div *= 2;

            for (int i = 0; i < cells; i += div)
                for (int j = 0; j < cells; j += div)
                    rotate(i, j, div);
            refresh();
        };

        int sum = 0;
        int max = 0;
        for (int i = 0; i < cells; i++)
            for (int j = 0; j < cells; j++) {
                sum += ice[i][j];

                if (!isVisited[i][j] && ice[i][j] != 0)
                    max = Math.max(max, dfs(i, j));
            }

        System.out.println(sum);
        System.out.println(max);
    }

    static void rotate(int sr, int sc, int size) {
        int[][] temp = new int[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                temp[j][size - 1 - i] = ice[sr + i][sc + j];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                ice[sr + i][sc + j] = temp[i][j];
    }

    static void refresh() {
        boolean[][] del = new boolean[cells][cells];

        for (int i = 0; i < cells; i++)
            for (int j = 0; j < cells; j++) {
                int cnt = 0;

                for (int[] d : dl) {
                    int nr = i + d[0];
                    int nc = j + d[1];

                    if (inRange(nr, nc) && ice[nr][nc] > 0)
                        cnt++;
                }

                if (cnt < 3)
                    del[i][j] = true;
            }

        for (int i = 0; i < cells; i++)
            for (int j = 0; j < cells; j++)
                if (del[i][j])
                    ice[i][j] = Math.max(0, ice[i][j] - 1);
    }

    static int dfs(int r, int c) {
        isVisited[r][c] = true;

        int ret = 1;

        for (int[] d : dl) {
            int nr = r + d[0];
            int nc = c + d[1];

            if (inRange(nr, nc) && ice[nr][nc] != 0 && !isVisited[nr][nc])
                ret += dfs(nr, nc);
        }

        return ret;
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < cells && c >= 0 && c < cells;
    }
}

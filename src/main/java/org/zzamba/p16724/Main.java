package org.zzamba.p16724;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, M, res;
    static String[] dir;
    static int[][] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        dir = new String[N];
        visited = new int[N][M];
        res = 0;
        IntStream.range(0, N).forEach(i -> dir[i] = sc.next());

        int nowColor = 1;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (visited[i][j] == 0)
                    check(i, j, nowColor++);

        System.out.print(res);
    }

    static void check(int row, int col, int color) {
        visited[row][col] = color;
        int[] dl = getDl(dir[row].charAt(col));

        int nextRow = row + dl[0];
        int nextCol = col + dl[1];

        if (visited[nextRow][nextCol] != 0) {
            if (visited[nextRow][nextCol] == color)
                res++;
            return;
        }

        check(nextRow, nextCol, color);
    }

    static int[] getDl(char c) {
        return switch (c) {
            case 'L' -> new int[]{0, -1};
            case 'R' -> new int[]{0, 1};
            case 'U' -> new int[]{-1, 0};
            case 'D' -> new int[]{1, 0};
            default -> throw new RuntimeException();
        };
    }
}

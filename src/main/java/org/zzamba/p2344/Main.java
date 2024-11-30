package org.zzamba.p2344;

import java.util.Scanner;

public class Main {
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int UP = 3;
    static final int[][] dl = { {0, 1}, {0, -1}, {-1, 0}, {1, 0} };

    static int N, M;
    static int[][] map; // 1: '/' 모양 거울
    // 거울은 L -> D, R -> U. U -> R, D -> L
    static int[][][] dp; // [들어온 방향][행][열]
    static StringBuilder sb;

    public static void main(String[] args) {
        sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N + 2][M + 2];
        dp = new int[4][N + 2][M + 2];
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= M; j++)
                map[i][j] = sc.nextInt();

        putHolesNum();
        flashAll();

        System.out.print(sb);
    }

    static void putHolesNum() {
        int num = 1;

        for (int i = 1; i <= N; i++)
            map[i][0] = num++;
        for (int i = 1; i <= M; i++)
            map[N + 1][i] = num++;
        for (int i = N; i >= 1; i--)
            map[i][M + 1] = num++;
        for (int i = M; i >= 1; i--)
            map[0][i] = num++;
    }

    static void flashAll() {
        for (int i = 1; i <= N; i++)
            sb.append(fdp(i, 1, LEFT)).append(' ');
        for (int i = 1; i <= M; i++)
            sb.append(fdp(N, i, DOWN)).append(' ');
        for (int i = N; i >= 1; i--)
            sb.append(fdp(i, M, RIGHT)).append(' ');
        for (int i = M; i >= 1; i--)
            sb.append(fdp(1, i, UP)).append(' ');
    }

    static int fdp(int row, int col, int dir) {
        if (dp[dir][row][col] != 0)
            return dp[dir][row][col];

        if (row == 0 || row == N + 1 || col == 0 || col == M + 1) {
            dp[dir][row][col] = map[row][col];
            return dp[dir][row][col];
        }

        int nextDir = dir;
        if (map[row][col] == 1)
            nextDir = (dir + 2) % 4;

        dp[dir][row][col] = fdp(row + dl[nextDir][0], col + dl[nextDir][1], nextDir);
        return dp[dir][row][col];
    }
}

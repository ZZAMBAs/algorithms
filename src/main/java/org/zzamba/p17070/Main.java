package org.zzamba.p17070;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static final int WALL = 1;
    static final int[][] DL = { {0, 1}, {1, 1}, {1, 0} };
    static final int[][][] CHECKING_POINT = { { {0, 1} }, { {1, 0}, {1, 1}, {0, 1} }, { {1, 0} } };

    static int N;
    static int[][] map;
    static int[][][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        map = new int[N][N];
        dp = new int[DL.length][N][N]; // { 방향, x, y }
        dp[0][0][1] = 1;

        for (int row = 0; row < N; row++)
            for (int col = 0; col < N; col++)
                map[row][col] = sc.nextInt();

        for (int row = 0; row < N; row++)
            for (int col = 0; col < N; col++)
                move(row, col);

        int res = IntStream.range(0, DL.length).map(d -> dp[d][N - 1][N - 1]).sum();
        System.out.print(res);
    }

    static void move(int row, int col) {
        for (int dIdx = 0; dIdx < DL.length; dIdx++)
			for (int dd = -1; dd <= 1; dd++) {
				int curD = dIdx + dd;

				if (curD < 0 || curD >= DL.length)
					continue;

				int nextR = row + DL[curD][0];
				int nextC = col + DL[curD][1];

				if (inRange(nextR, nextC) && canMove(row, col, curD))
					dp[curD][nextR][nextC] += dp[dIdx][row][col];
			}
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    static boolean canMove(int row, int col, int d) {
        for (int[] c : CHECKING_POINT[d]) {
            int chkRow = row + c[0];
            int chkCol = col + c[1];

            if (map[chkRow][chkCol] == WALL)
                return false;
        }

        return true;
    }
}

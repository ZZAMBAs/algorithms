package org.zzamba.p12100;

import java.util.*;

// 나는 모든 방향에 대해 빡구현했지만, 다음 코드가 훨씬 좋은 구현이라 생각한다.
// https://www.acmicpc.net/source/104642105
// 위, 아래, 왼쪽, 오른쪽으로 이동 시키는 것은 이동시키는 방향을 기준점으로 했을 때, 로직이 동일하다는 점을 이용한 코드다.
public class Main {
    static Scanner sc = new Scanner(System.in);
    static int N, maxBlock;

    public static void main(String[] args) {
        N = sc.nextInt();
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                board[i][j] = sc.nextInt();

        dfs(board, 1);

        System.out.print(maxBlock);
    }

    static void dfs(int[][] board, int count) {
        if (count > 5)
            return;

        up(board, new TempBoardInfo(), count);
        left(board, new TempBoardInfo(), count);
        down(board, new TempBoardInfo(), count);
        right(board, new TempBoardInfo(), count);
    }

    static void up(int[][] board, TempBoardInfo tInfo, int count) {
        int[][] tBoard = tInfo.tempBoard;

        for (int c = 0; c < N; c++) {
            tInfo.tempC = c;
            tInfo.tempR = 0;
            tInfo.isMerged = false;
            for (int r = 0; r < N; r++) {
                if (board[r][c] == 0)
                    continue;

                if (tBoard[tInfo.tempR][tInfo.tempC] == board[r][c] && !tInfo.isMerged) {
                    tBoard[tInfo.tempR][tInfo.tempC] *= 2;
                    tInfo.isMerged = true;
                } else if (tBoard[tInfo.tempR][tInfo.tempC] == 0) {
                    tBoard[tInfo.tempR][tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                } else {
                    tBoard[++tInfo.tempR][tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                }
            }
        }

        //print(tBoard, "method: up, count: " + count);
        updateMaxBlock(tBoard);
        dfs(tBoard, count + 1);
    }

    static void print(int[][] matrix, String msg) {
        System.out.println("--" + msg + "--");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    static void left(int[][] board, TempBoardInfo tInfo, int count) {
        int[][] tBoard = tInfo.tempBoard;

        for (int r = 0; r < N; r++) {
            tInfo.tempR = r;
            tInfo.tempC = 0;
            tInfo.isMerged = false;
            for (int c = 0; c < N; c++) {
                if (board[r][c] == 0)
                    continue;

                if (tBoard[tInfo.tempR][tInfo.tempC] == board[r][c] && !tInfo.isMerged) {
                    tBoard[tInfo.tempR][tInfo.tempC] *= 2;
                    tInfo.isMerged = true;
                } else if (tBoard[tInfo.tempR][tInfo.tempC] == 0) {
                    tBoard[tInfo.tempR][tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                } else {
                    tBoard[tInfo.tempR][++tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                }
            }
        }

        //print(tBoard, "method: left, count: " + count);
        updateMaxBlock(tBoard);
        dfs(tBoard, count + 1);
    }

    static void down(int[][] board, TempBoardInfo tInfo, int count) {
        int[][] tBoard = tInfo.tempBoard;

        for (int c = 0; c < N; c++) {
            tInfo.tempC = c;
            tInfo.tempR = N - 1;
            tInfo.isMerged = false;
            for (int r = N - 1; r >= 0; r--) {
                if (board[r][c] == 0)
                    continue;

                if (tBoard[tInfo.tempR][tInfo.tempC] == board[r][c] && !tInfo.isMerged) {
                    tBoard[tInfo.tempR][tInfo.tempC] *= 2;
                    tInfo.isMerged = true;
                } else if (tBoard[tInfo.tempR][tInfo.tempC] == 0) {
                    tBoard[tInfo.tempR][tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                } else {
                    tBoard[--tInfo.tempR][tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                }
            }
        }

        updateMaxBlock(tBoard);
        dfs(tBoard, count + 1);
    }

    static void right(int[][] board, TempBoardInfo tInfo, int count) {
        int[][] tBoard = tInfo.tempBoard;

        for (int r = 0; r < N; r++) {
            tInfo.tempR = r;
            tInfo.tempC = N - 1;
            for (int c = N - 1; c >= 0; c--) {
                if (board[r][c] == 0)
                    continue;

                if (tBoard[tInfo.tempR][tInfo.tempC] == board[r][c] && !tInfo.isMerged) {
                    tBoard[tInfo.tempR][tInfo.tempC] *= 2;
                    tInfo.isMerged = true;
                } else if (tBoard[tInfo.tempR][tInfo.tempC] == 0) {
                    tBoard[tInfo.tempR][tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                } else {
                    tBoard[tInfo.tempR][--tInfo.tempC] = board[r][c];
                    tInfo.isMerged = false;
                }
            }
        }

        updateMaxBlock(tBoard);
        dfs(tBoard, count + 1);
    }

    static void updateMaxBlock(int[][] board) {
        for (int[] row : board)
            for (int v : row)
                maxBlock = Math.max(maxBlock, v);
    }

    static class TempBoardInfo {
        int[][] tempBoard = new int[N][N];
        int tempC;
        int tempR;
        boolean isMerged;
    }
}

package org.zzamba.p20165;

import java.util.Scanner;

public class Main {
    static int N, M, R, res;
    static int[][] initMap, curMap; // 상태 저장 최초 맵, 진행 중인 맵. 0: 넘어짐. 그 외는 도미노 길이

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0;
        N = sc.nextInt();
        M = sc.nextInt();
        R = sc.nextInt();
        initMap = new int[N][M];
        curMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                initMap[i][j] = sc.nextInt();
                curMap[i][j] = initMap[i][j];
            }
        }

        for (int i = 0; i < R; i++) {
            int attackRow = sc.nextInt() - 1;
            int attackCol = sc.nextInt() - 1;
            Direction d = Direction.valueOf(sc.next());

            int length = curMap[attackRow][attackCol];
            if (length <= 0)
                continue;

            while (length > 0 && inRange(attackRow, attackCol)) {
                length = Math.max(length - 1, curMap[attackRow][attackCol] - 1);

                if (curMap[attackRow][attackCol] > 0)
                    res++;

                curMap[attackRow][attackCol] = 0;
                attackRow += d.deltaRow;
                attackCol += d.deltaCol;
            }

            int defenseRow = sc.nextInt() - 1;
            int defenseCol = sc.nextInt() - 1;
            curMap[defenseRow][defenseCol] = initMap[defenseRow][defenseCol];
        }

        System.out.println(res);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                System.out.print((curMap[i][j] > 0 ? "S" : "F") + " ");
            System.out.println();
        }
    }

    static boolean inRange(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    enum Direction {
        E(0, 1), W(0, -1), S(1, 0), N(-1, 0);

        final int deltaRow;
        final int deltaCol;

        Direction(int deltaRow, int deltaCol) {
            this.deltaRow = deltaRow;
            this.deltaCol = deltaCol;
        }
    }
}

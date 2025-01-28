package org.zzamba.p16432;

import java.util.*;

public class Main {
    static int N;
    static boolean[][] nDays;
    static boolean[][] canReach;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        nDays = new boolean[N][10];
        canReach = new boolean[N][10];
        for (int i = 0; i < N; i++) {
            int M = sc.nextInt();
            for (int j = 0; j < M; j++)
                nDays[i][sc.nextInt()] = true;
        }
        for (int i = 0; i <= 9; i++)
            canReach[0][i] = nDays[0][i];

        for (int i = 0; i < N - 1; i++)
            for (int j = 1; j <= 9; j++)
                for (int k = 1; k <= 9; k++) {
                    if (j == k || !canReach[i][j] || !nDays[i + 1][k])
                        continue;

                    canReach[i + 1][k] = true;
                }

        int col = 0;
        for (int i = 1; i <= 9; i++)
            if (canReach[N - 1][i]) {
                col = i;
                break;
            }

        if (col == 0) {
            System.out.println(-1);
            return;
        }

        Deque<Integer> stk = new ArrayDeque<>();
        for (int i = N - 1; i >= 0; i--) {
            stk.push(col);

            if (i == 0)
                break;

            for (int j = 1; j <= 9; j++) {
                if (col == j)
                    continue;
                if (canReach[i - 1][j]) {
                    col = j;
                    break;
                }
            }
        }

        while (!stk.isEmpty())
            System.out.println(stk.pop());
    }
}

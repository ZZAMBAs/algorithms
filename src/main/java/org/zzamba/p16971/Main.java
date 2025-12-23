package org.zzamba.p16971;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, maxV = Integer.MIN_VALUE, originalSum = 0;
    static int[] rowSum, colSum;
    static int[][] A;

    public static void main(String[] args) throws Exception {
        String[] s = br.readLine().split(" ");
        N = Integer.parseInt(s[0]);
        M = Integer.parseInt(s[1]);

        rowSum = new int[N];
        colSum = new int[M];
        A = new int[N][M];

        for (int i = 0; i < N; i++)
            A[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                int mul;

                if (r == 0 || r == N - 1)
                    mul = 1;
                else
                    mul = 2;


                int sumV = (c == 0 || c == M - 1) ? A[r][c] * mul : A[r][c] * mul * 2;

                rowSum[r] += sumV;
                colSum[c] += sumV;
                originalSum += sumV;
            }
        }
        maxV = originalSum;

        change();

        System.out.print(maxV);
    }

    static void change() {
        int[] standardRows = new int[]{0, N - 1};
        int[] standardCols = new int[]{0, M - 1};

        for (int row : standardRows)
            for (int i = 1; i < N - 1; i++)
                maxV = Math.max(maxV, originalSum + rowSum[row] - rowSum[i] / 2);

        for (int col : standardCols)
            for (int i = 1; i < M - 1; i++)
                maxV = Math.max(maxV, originalSum + colSum[col] - colSum[i] / 2);

    }
}

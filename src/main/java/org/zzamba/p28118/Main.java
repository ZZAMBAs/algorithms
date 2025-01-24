package org.zzamba.p28118;

import java.io.*;
import java.util.stream.IntStream;

// Union-Find로 쉽게 해결 가능
public class Main {
    static int N, M, res;
    static int[][] adj;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inp = br.readLine().split(" ");
        N = Integer.parseInt(inp[0]);
        M = Integer.parseInt(inp[1]);
        res = 0;
        adj = new int[N][N];
        IntStream.range(0, M).forEach(i -> {
			try {
                String[] edge = br.readLine().split(" ");

                int a = Integer.parseInt(edge[0]) - 1;
                int b = Integer.parseInt(edge[1]) - 1;
                adj[a][b] = 1;
                adj[b][a] = 1;
            } catch (Exception e) {}
        });

        cal();

        System.out.print(res);
    }

    static void cal() {
        int falseCnt = 0;
        boolean sw = true;
        while (falseCnt != 2) {
            sw = fill(sw);

            if (sw)
                falseCnt = 0;
            else
                falseCnt++;
        }
    }

    static boolean fill(boolean sw) { // true: 0만 다 채움, false: 처음으로 마주치는 1 채움.
        boolean ret = false;

        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < N; k++) {
                    int val = 2 - (adj[i][j] + adj[j][k] + adj[k][i]);

                    if (val == 0) {
                        adj[i][j] = adj[j][i] = adj[j][k] = adj[k][j] = adj[i][k] = adj[k][i] = 1;
                        ret = true;
                    }
                    if (val == 1 && !sw) {
                        adj[i][j] = adj[j][i] = adj[j][k] = adj[k][j] = adj[i][k] = adj[k][i] = 1;
                        res++;
                        return true;
                    }
                }

        return ret;
    }

}

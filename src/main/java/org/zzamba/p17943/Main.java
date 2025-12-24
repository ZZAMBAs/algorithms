package org.zzamba.p17943;

import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, Q;
    static int[] preXor, xors;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inp[0];
        Q = inp[1];
        xors = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        preXor = new int[N];
        for (int i = 1; i < N; i++)
            preXor[i] = preXor[i - 1] ^ xors[i - 1];

        for (int i = 0; i < Q; i++) {
            int[] cmd = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            switch(cmd[0]) {
                case 0 -> sb.append(calXor(cmd[1], cmd[2]));
                case 1 -> sb.append(calY(cmd[1], cmd[2], cmd[3]));
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }

    static int calXor(int x, int y) {
        return preXor[x - 1] ^ preXor[y - 1];
    }

    static int calY(int x, int y, int d) {
        return calXor(x, y) ^ d;
    }
}

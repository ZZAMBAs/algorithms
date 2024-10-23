package org.zzamba.p20955;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    static int N;
    static int M;
    static int res;
    static int[] par;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inp[0];
        M = inp[1];
        res = 0;
        par = new int[N + 1];
        IntStream.rangeClosed(0, N).forEach(i -> par[i] = i);

        for (int i = 0; i < M; i++) {
            inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            union(inp[0], inp[1]);
        }

        int preIdx = 0;
        for (int i = 1; i <= N; i++) {
            if (find(i) != find(preIdx)) {
                res++;
                union(i, preIdx);
                preIdx = i;
            }
        }

        res--;

        res += M + res - N + 1;
        System.out.println(res);
    }

    static int find(int idx) {
        if (par[idx] == idx)
            return idx;

        par[idx] = find(par[idx]);
        return par[idx];
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a != b) {
            int aPar = find(a);
            int bPar = find(b);

            par[bPar] = aPar;
        }
    }
}

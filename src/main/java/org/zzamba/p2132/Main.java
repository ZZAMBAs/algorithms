package org.zzamba.p2132;

import java.util.*;
import java.io.*;

// 정답이지만, 최적 해법은 트리의 지름이다.
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] fruits;
    static int n, start = -1;
    static long maxFruitsEaten = -1;
    static List<List<Integer>> adj = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());

        fruits = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < n - 1; i++) {
            int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            adj.get(inp[0] - 1).add(inp[1] - 1);
            adj.get(inp[1] - 1).add(inp[0] - 1);
        }

        for (int i = 0; i < n; i++) {
            long ret = dfs(i, -1); // 열매 먹은 개수

            if (maxFruitsEaten < ret) {
                maxFruitsEaten = ret;
                start = i + 1;
            }
        }

        System.out.print(maxFruitsEaten + " " + start);
    }

    static long dfs(int v, int pre) {
        long nextFruitsEaten = 0L;

        for (int next : adj.get(v)) {
            if (next == pre)
                continue;

            nextFruitsEaten = Math.max(nextFruitsEaten, dfs(next, v));
        }

        return fruits[v] + nextFruitsEaten;
    }
}

package org.zzamba.p17260;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] limit, H;
    static int N, K;
    static List<List<Integer>> adj = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        int[] inp = readLineWithInt();
        N = inp[0];
        K = inp[1];
        H = readLineWithInt();

        for (int i = 0; i < N; i++)
            adj.add(new ArrayList<>());

        for (int i = 0; i < N - 1; i++) {
            inp = readLineWithInt();
            adj.get(inp[0] - 1).add(inp[1] - 1);
            adj.get(inp[1] - 1).add(inp[0] - 1);
        }
        limit = new int[N];
        Arrays.fill(limit, -1);

        System.out.print(bfs(K - 1));
    }

    static int bfs(int start) {
        if (N == 1)
            return 1;

        Queue<Integer> q = new LinkedList<>();
        limit[start] = 0;

        for (int adv : adj.get(start)) {
            limit[adv] = H[start];
            q.add(adv);
        }

        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int curIdx = q.poll();

                if (limit[curIdx] <= H[curIdx])
                    return 1;

                for (int adv : adj.get(curIdx)) {
                    if (limit[adv] >= 0)
                        continue;

                    limit[adv] = Math.min(2 * limit[curIdx] - H[curIdx], 1000001);
                    q.add(adv);
                }
            }
        }

        return 0;
    }

    static int[] readLineWithInt() throws Exception {
        return Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

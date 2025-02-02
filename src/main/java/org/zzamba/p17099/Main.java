package org.zzamba.p17099;

import java.io.*;
import java.util.*;

// 자바로 시간 초과 해결을 할 수 없다. (C++ 변환 후 정답처리)
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int res = 0;
        int[][] inputs = new int[N][3];
        for (int i = 0; i < N; i++) {
            String[] inp = br.readLine().split(" ");
            inputs[i][0] = Integer.parseInt(inp[0]);
            inputs[i][1] = Integer.parseInt(inp[1]);
            inputs[i][2] = Integer.parseInt(inp[2]);
        }
        Arrays.sort(inputs, Comparator.comparingInt(o -> o[0]));

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0])
                return o2[1] - o1[1];
            return o1[0] - o2[0];
        });

        for (int[] p : inputs) {
            while (!pq.isEmpty() && pq.peek()[0] < p[0])
                res = Math.max(res, pq.poll()[1]);

            pq.add(new int[]{p[1], res + p[2]});
        }

        while (!pq.isEmpty())
            res = Math.max(res, pq.poll()[1]);

        System.out.print(res);
    }
}

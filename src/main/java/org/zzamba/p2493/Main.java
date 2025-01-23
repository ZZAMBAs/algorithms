package org.zzamba.p2493;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Deque<int[]> stk = new ArrayDeque<>(); // {값, idx}
        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        for (int idx = 1; idx <= N; idx++) {
            int curV = inp[idx - 1];

            while (!stk.isEmpty() && stk.peek()[0] < curV)
                stk.pop();

            if (stk.isEmpty())
                sb.append(0).append(" ");
            else
                sb.append(stk.peek()[1]).append(" ");

            stk.push(new int[]{curV, idx});
        }

        System.out.print(sb);
    }
}

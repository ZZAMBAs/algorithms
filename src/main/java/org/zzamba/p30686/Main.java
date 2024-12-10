package org.zzamba.p30686;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, M, res;
    static int[] rememberTime;
    static List<List<Integer>> requiring;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = Integer.MAX_VALUE;
        N = sc.nextInt();
        M = sc.nextInt();
        visited = new boolean[M];
        requiring = new ArrayList<>();
        rememberTime = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        for (int i = 0; i < M; i++)
            requiring.add(IntStream.range(0, sc.nextInt()).mapToObj(j -> sc.nextInt() - 1).collect(Collectors.toList()));

        bruteforce(0, new ArrayList<>());

        System.out.print(res);
    }

    static void bruteforce(int idx, List<Integer> seq) {
        if (idx == M) {
            res = Math.min(res, cal(seq));
            return;
        }

        for (int i = 0; i < M; i++) {
            if (visited[i])
                continue;

            visited[i] = true;
            seq.add(i);
            bruteforce(idx + 1, seq);
            seq.remove(seq.size() - 1);
            visited[i] = false;
        }
    }

    static int cal(List<Integer> seq) {
        int ret = 0;
        int[] time = new int[N];

        for (int curSeq : seq) {
            for (int knowledge: requiring.get(curSeq)) {
                if (time[knowledge] == 0) {
                    time[knowledge] = rememberTime[knowledge];
                    ret++;
                }
            }

            IntStream.range(0, N).forEach(i -> time[i] = Math.max(0, time[i] - 1));
        }

        return ret;
    }
}

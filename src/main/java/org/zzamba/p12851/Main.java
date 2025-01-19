package org.zzamba.p12851;

import java.util.*;
import java.util.function.*;

public class Main {
    static final int MAP_SIZE = 100_000;

    static List<Function<Integer, Integer>> nextFuncs;
    static int N, K;

    public static void main(String[] args) {
        init();
        ResultSet rs = cal();

        System.out.println(rs.minTime);
        System.out.println(rs.cnt);
    }

    static void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        nextFuncs = new ArrayList<>();
        nextFuncs.add(x -> x + 1);
        nextFuncs.add(x -> x - 1);
        nextFuncs.add(x -> 2 * x);
    }

    static ResultSet cal() {
        int minTime = 0;
        int[] dp = new int[MAP_SIZE + 1];
        boolean[] isVisited = new boolean[MAP_SIZE + 1];
        isVisited[N] = true;
        dp[N] = 1;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(N);
        while (!q.isEmpty() && !isVisited[K]) {
            Set<Integer> lazyVisited = new HashSet<>(); // isVisited 지연 갱신용
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int cur = q.poll();

                for (Function<Integer, Integer> func : nextFuncs) {
                    int next = func.apply(cur);

                    if (inRange(next) && !isVisited[next]) {
                        lazyVisited.add(next);
                        dp[next] += dp[cur];
                    }
                }
            }
            minTime++;
            lazyVisited.forEach(i -> {
                isVisited[i] = true;
                q.add(i);
            });
        }

        return new ResultSet(minTime, dp[K]);
    }

    static boolean inRange(int loc) {
        return loc >= 0 && loc <= MAP_SIZE;
    }

    static class ResultSet{
        int minTime;
        int cnt;

        public ResultSet(int minTime, int cnt) {
            this.minTime = minTime;
            this.cnt = cnt;
        }
    }
}

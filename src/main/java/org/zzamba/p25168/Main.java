package org.zzamba.p25168;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, M;
    static List<List<Pair<Integer, Integer>>> outDeg; // { 다음, 최소 대기 시간 }
    static boolean[] isStart;
    static int[] days;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        outDeg = new ArrayList<>();
        N = sc.nextInt();
        M = sc.nextInt();
        isStart = new boolean[N + 1];
        days = new int[N + 1];
        Arrays.fill(isStart, true);
        Arrays.fill(days, 1);
        IntStream.rangeClosed(0, N).forEach(i -> outDeg.add(new ArrayList<>()));
        IntStream.range(0, M).forEach(i -> {
            int pre = sc.nextInt();
            int next = sc.nextInt();
            outDeg.get(pre).add(new Pair<>(next, sc.nextInt()));
            isStart[next] = false;
        });

        bfs();

        IntStream.rangeClosed(1, N).map(i -> days[i]).max().ifPresent(System.out::print);
    }

    static void bfs() {
        Queue<Integer> q = new ArrayDeque<>(IntStream.rangeClosed(1, N).filter(i -> isStart[i]).boxed().collect(Collectors.toList()));
        boolean[] isVisited = new boolean[N + 1];

        while (!q.isEmpty()) {
            int qSize = q.size();

            for (int i = 0; i < qSize; i++) {
                int top = q.poll();

                outDeg.get(top).forEach(pair -> {
                    if (!isVisited[pair.t])
                        q.add(pair.t);

                    isVisited[pair.t] = true;
                    int nextDay = days[top] + pair.r + (pair.r >= 7 ? 1 : 0);
                    days[pair.t] = Math.max(days[pair.t], nextDay);
                });
            }
        }

    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }
    }
}

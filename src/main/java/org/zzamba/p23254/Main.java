package org.zzamba.p23254;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N, M, res;
    static int[] initScores, plusScores;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt() * 24;
        M = sc.nextInt();
        initScores = IntStream.range(0, M).map(i -> sc.nextInt()).toArray();
        plusScores = IntStream.range(0, M).map(i -> sc.nextInt()).toArray();
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.<Pair<Integer, Integer>, Integer>comparing(Pair::getT).reversed()); // { 시간 당 증가, 투자 시간 }
        IntStream.range(0, M).forEach(i -> {
            res += initScores[i];

            int q = (100 - initScores[i]) / plusScores[i];
            int r = 100 - initScores[i] - plusScores[i] * q;

            pq.add(new Pair<>(plusScores[i], q));

            if (r != 0)
                pq.add(new Pair<>(r, 1));
        });

        // 1. 공부 다 해서 시간이 남는 경우 (올100)
        // 2. 전부 공부할 시간이 없는 경우

        while (!pq.isEmpty() && N > 0) {
            Pair<Integer, Integer> top = pq.poll();

            int consumeTime = Math.min(N, top.r);
            N -= consumeTime;
            res += top.t * consumeTime;
        }

        System.out.print(res);
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        public T getT() {
            return t;
        }
    }
}

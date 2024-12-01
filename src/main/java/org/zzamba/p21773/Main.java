package org.zzamba.p21773;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int n, T;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        T = sc.nextInt();
        n = sc.nextInt();

        Comparator<Triple<Integer, Integer, Integer>> tripleComparator = Comparator.<Triple<Integer, Integer, Integer>, Integer>comparing(Triple::getT)
            .reversed()
            .thenComparing(Triple::getR);
        PriorityQueue<Triple<Integer, Integer, Integer>> pq = new PriorityQueue<>(tripleComparator); // {우선순위, id, 남은 실행 시간}

        IntStream.range(0, n).forEach(i -> {
            int id = sc.nextInt();
            int restTime = sc.nextInt();
            int priority = sc.nextInt();

            pq.add(new Triple<>(priority, id, restTime));
        });

        for (int i = 1; i <= T; i++) {
            Triple<Integer, Integer, Integer> top = pq.poll();

            sb.append(top.r).append('\n');

            if (top.u - 1 > 0)
                pq.add(new Triple<>(top.t - 1, top.r, top.u - 1));
        }

        System.out.print(sb);
    }

    static class Triple<T, R, U> {
        T t;
        R r;
        U u;

        public Triple(T t, R r, U u) {
            this.t = t;
            this.r = r;
            this.u = u;
        }

        public T getT() {
            return t;
        }

        public R getR() {
            return r;
        }

    }
}

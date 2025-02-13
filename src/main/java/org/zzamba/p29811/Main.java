package org.zzamba.p29811;

import java.util.*;
import java.util.stream.IntStream;

// 우선순위 큐 풀이, BBST 풀이 존재
public class Main {
    static int N, M;
    static int[] first, second;
    static TreeSet<Pair<Integer, Integer>> firstPath, secondPath;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        first = new int[N];
        second = new int[N];

        first = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        second = IntStream.range(0, M).map(i -> sc.nextInt()).toArray();
        firstPath = new TreeSet<>(Comparator.comparing(Pair<Integer, Integer>::getT).thenComparing(Pair::getR));
        secondPath = new TreeSet<>(Comparator.comparing(Pair<Integer, Integer>::getT).thenComparing(Pair::getR));
        for (int i = 0; i < N; i++)
            firstPath.add(new Pair<>(first[i], i));
        for (int i = 0; i < M; i++)
            secondPath.add(new Pair<>(second[i], i));


        int K = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        while (K-- > 0) {
            String cmd = sc.next();

            switch (cmd.charAt(0)) {
                case 'U' -> update(sc.nextInt() - 1, sc.nextInt());
                case 'L' -> cal(sb);
            }
        }

        System.out.print(sb);
    }

    static void update(int idx, int updateVal) {
        if (idx < N) {
            firstPath.remove(new Pair<>(first[idx], idx));
            first[idx] = updateVal;
            firstPath.add(new Pair<>(first[idx], idx));
        } else {
            secondPath.remove(new Pair<>(second[idx - N], idx - N));
            second[idx - N] = updateVal;
            secondPath.add(new Pair<>(second[idx - N], idx - N));

        }
    }

    static void cal(StringBuilder sb) {
        sb.append(firstPath.first().r + 1).append(' ').append(secondPath.first().r + N + 1).append('\n');
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

        public R getR() {
            return r;
        }
    }

}

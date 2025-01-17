package org.zzamba.p17298;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N;
    static int[] NGEs;
    static Deque<Pair<Integer, Integer>> stk; // { 값, idx }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        stk = new ArrayDeque<>();
        N = sc.nextInt();
        NGEs = new int[N];
        Arrays.fill(NGEs, -1);
        IntStream.range(0, N).forEach(i -> {
            int curV = sc.nextInt();

            while (true) {
                if (stk.isEmpty() || stk.peek().t >= curV) {
                    stk.push(new Pair<>(curV, i));
                    break;
                }

                Pair<Integer, Integer> poppedPair = stk.pop();
                NGEs[poppedPair.r] = curV;
            }
        });

        System.out.print(Arrays.stream(NGEs).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
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

package org.zzamba.p7662;

import java.io.*;
import java.util.*;

public class Main {
    static int T, k;
    static TreeSet<Pair<Integer, Integer>> doubleEndedPriorityQ;
    static Map<Integer, Integer> cnt;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            k = Integer.parseInt(br.readLine());
            doubleEndedPriorityQ = new TreeSet<>(
                Comparator.comparing(Pair<Integer, Integer>::getT)
                    .thenComparing(Pair<Integer, Integer>::getR)
            );
            cnt = new HashMap<>();

            for (int j = 0; j < k; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String cmd = st.nextToken();
                int val = Integer.parseInt(st.nextToken());

                if (cmd.equals("I"))
                    insert(val);
                else
                    delete(val == 1);
            }

            if (doubleEndedPriorityQ.isEmpty())
                sb.append("EMPTY\n");
            else
                sb.append(getMax()).append(' ').append(getMin()).append('\n');

            System.gc();
        }
        System.out.print(sb);
    }

    static void insert(int val) {
        cnt.put(val, cnt.getOrDefault(val, 0) + 1);
        doubleEndedPriorityQ.add(new Pair<>(val, cnt.get(val)));
    }

    static void delete(boolean isMax) {
        Pair<Integer, Integer> polled;
        if (isMax)
            polled = doubleEndedPriorityQ.pollLast();
        else
            polled = doubleEndedPriorityQ.pollFirst();

        if (polled == null)
            return;
    }

    static int getMax() {
        return doubleEndedPriorityQ.last().t;
    }

    static int getMin() {
        return doubleEndedPriorityQ.first().t;
    }

    static class Pair<T, R> {
        T t;
        R r;

        Pair(T t, R r) {
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

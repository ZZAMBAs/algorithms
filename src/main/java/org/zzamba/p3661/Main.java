package org.zzamba.p3661;

import java.io.*;
import java.util.*;
import java.util.stream.*;

// PQ 쓸 상황에 정렬로 할 수 있을지 다시 고민할 것
public class Main {
    static int p, n, rest;
    static int[] res, limit;

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            String[] v = br.readLine().split(" ");
            p = Integer.parseInt(v[0]);
            n = Integer.parseInt(v[1]);
            rest = p % n;

            res = new int[n];
            v = br.readLine().split(" ");
            limit = Arrays.stream(v).mapToInt(Integer::parseInt).toArray();

            if (Arrays.stream(limit).sum() < p) {
                sb.append("IMPOSSIBLE").append('\n');
                continue;
            }

            IntStream.range(0, n).forEach(i -> {
                res[i] = p / n;

                if (res[i] > limit[i]) {
                    rest += res[i] - limit[i];
                    res[i] = limit[i];
                }

            });

            // {limit, idx}
            List<Pair<Integer, Integer>> temp = new ArrayList<>();

            IntStream.range(0, n).forEach(i -> temp.add(new Pair<>(limit[i], i)));
            temp.sort(Comparator.<Pair<Integer, Integer>, Integer>comparing(Pair::getT).reversed().thenComparing(Pair::getR));

            int cursor = 0;
            while (rest-- > 0) {
                if (res[temp.get(cursor).r] == temp.get(cursor).t)
                    cursor = 0;

                res[temp.get(cursor).r]++;
                cursor++;
            }

            IntStream.range(0, n).forEach(i -> {
                sb.append(res[i]).append(' ');
            });
            sb.append('\n');
        }

        System.out.print(sb);
    }

    static class Pair<T, R> {
        T t;
        R r;
        Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        T getT() {
            return t;
        }

        R getR() {
            return r;
        }
    }
}

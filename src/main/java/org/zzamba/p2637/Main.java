package org.zzamba.p2637;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, M;
    static int[] needs;
    static List<List<Pair<Integer, Integer>>> inDeg; // {부품, 필요 수}
    static Map<Integer, Map<Integer, Integer>> dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        inDeg = new ArrayList<>();
        needs = new int[N];
        dp = new HashMap<>();
        IntStream.rangeClosed(0, N).forEach(i -> {
            inDeg.add(new ArrayList<>());
            dp.put(i, new HashMap<>());
        });
        IntStream.range(0, M).forEach(i -> {
            try {
                int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                int finish = inp[0];
                int ingredient = inp[1];
                int num = inp[2];

                inDeg.get(finish).add(new Pair<>(ingredient, num));
            } catch (Exception e) {}
        });

        cal(N, 1).entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }

    static Map<Integer, Integer> cal(int part, int cnt) { // 부품, 만드는 개수
        Map<Integer, Integer> curPartDp = dp.get(part);
        Map<Integer, Integer> ret = new HashMap<>();

        if (!curPartDp.isEmpty()) {
            curPartDp.entrySet().forEach(e -> ret.put(e.getKey(), e.getValue() * cnt));
            return ret;
        }

        if (inDeg.get(part).isEmpty()) {
            ret.put(part, cnt);
            return ret;
        }

        inDeg.get(part).forEach(in -> {
            Map<Integer, Integer> calMapPerOne = cal(in.t, in.r);
            for (Map.Entry<Integer, Integer> entry : calMapPerOne.entrySet()) {
                curPartDp.putIfAbsent(entry.getKey(), 0);
                curPartDp.put(entry.getKey(), curPartDp.get(entry.getKey()) + entry.getValue());

                ret.putIfAbsent(entry.getKey(), 0);
                ret.put(entry.getKey(), ret.get(entry.getKey()) + entry.getValue() * cnt);
            }
        });

        return ret;
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

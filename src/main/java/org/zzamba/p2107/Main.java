package org.zzamba.p2107;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static int N;
    static List<Pair<Integer, Integer>> ranges;
    static int[] counts;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        counts = new int[N];
        ranges = IntStream.range(0, N).mapToObj(i -> {
            int[] inp = null;

			try {
                inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			} catch (IOException e) {}
            return new Pair<>(inp[0], inp[1]);
        }).collect(Collectors.toList());
        ranges.sort(Comparator.comparing(Pair::getT));

        for (int i = 0; i < N; i++) {
            Pair<Integer, Integer> curP = ranges.get(i);

            for (int j = i + 1; j < N; j++) {
                Pair<Integer, Integer> nextP = ranges.get(j);

                if (nextP.t > curP.r)
                    break;

                if (nextP.r < curP.r)
                    counts[i]++;
            }
        }

        System.out.println(Arrays.stream(counts).max().getAsInt());
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

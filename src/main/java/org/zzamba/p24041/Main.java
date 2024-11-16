package org.zzamba.p24041;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, G, K;
    static PriorityQueue<Long> pq;
    static List<Ingredient> ingredients;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inp[0];
        G = inp[1];
        K = inp[2];
        ingredients = IntStream.range(0, N).mapToObj(i -> {
			try {
				int[] inp2 = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                return new Ingredient(inp2[0], inp2[1], inp2[2] == 0);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
        }).collect(Collectors.toList());

        System.out.print(cal());
    }

    static long cal() {
        long s = 0;
        long e = Integer.MAX_VALUE;

        while (s <= e) {
            long mid = (s + e) / 2;
            pq = new PriorityQueue<>(Comparator.reverseOrder());

            long sum = ingredients.stream().mapToLong(ingredient -> {
                long bacteria = ingredient.calBacteria(mid);

                if (!ingredient.isNecessary)
                    pq.add(bacteria);

                return bacteria;
            }).sum();

            for (int i = 0; i < K && !pq.isEmpty(); i++)
                sum -= pq.poll();

            if (sum > G)
                e = mid - 1;
            else
                s = mid + 1;
        }

        return e;
    }

    static class Ingredient {
        long S;
        long L;
        boolean isNecessary;

        public Ingredient(long s, long l, boolean isNecessary) {
            S = s;
            L = l;
            this.isNecessary = isNecessary;
        }

        public long calBacteria(long day) {
            return S * Math.max(1L, day - L);
        }
    }
}

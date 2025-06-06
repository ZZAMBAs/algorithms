package org.zzamba.p20943;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, lineNum;
    static long res;
    static Map<Pair, Integer> gradients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        IntStream.range(0, N).forEach(i -> {
            try {
                int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                Pair p = new Pair(inp[0], inp[1]);

                gradients.putIfAbsent(p, 0);
                gradients.put(p, gradients.get(p) + 1);

                lineNum++;
            } catch (Exception e) {}
        });

        gradients.entrySet().forEach(e -> {
            int v = e.getValue();
            res += (long)v * (lineNum - v);
        });

        System.out.print(res / 2L);
    }

    static class Pair {
        int a;
        int b;

        Pair (int a, int b) {
            boolean isNegative = (long)a * b < 0L;
            a = Math.abs(a);
            b = Math.abs(b);

            int gcd = gcd(a, b);

            if (gcd == 0) {
                this.a = a == 0 ? 0 : 1;
                this.b = b == 0 ? 0 : 1;
            }
            else {
                this.a = (a / gcd) * (isNegative ? -1 : 1);
                this.b = b / gcd;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass())
                return false;

            Pair other = (Pair) o;
            return this.a == other.a && this.b == other.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        private int gcd(int big, int small) {
            if (big < small) {
                int temp = small;
                small = big;
                big = temp;
            }

            if (small == 0)
                return 0;

            return gcd(small, big % small);
        }
    }
}

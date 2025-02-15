package org.zzamba.p17789;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int p, v;
    static int[] res;
    static List<Triple<Integer, Integer, Integer>> vases, pedestals;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        p = sc.nextInt();
        v = sc.nextInt();
        res = new int[v];
        pedestals = IntStream.range(0, p).mapToObj(i -> {
            int dia1 = sc.nextInt();
            int dia2 = sc.nextInt();

            if (dia1 > dia2) {
                int temp = dia1;
                dia1 = dia2;
                dia2 = temp;
            }

            return new Triple<>(dia1, dia2, i);
        }).sorted(Comparator.comparing(Triple<Integer, Integer, Integer>::getT).thenComparing(Triple::getR)).collect(Collectors.toList());
        vases = IntStream.range(0, v).mapToObj(i -> new Triple<>(sc.nextInt(), i, 0)).sorted(Comparator.comparing(Triple<Integer, Integer, Integer>::getT).thenComparing(Triple::getR)).collect(Collectors.toList());
        int pedestalIdx = 0;
        for (int i = 0; i < v; i++) {
            int curVase = vases.get(i).t;

            while (pedestalIdx < p && pedestals.get(pedestalIdx).t <= curVase) {
                if (pedestals.get(pedestalIdx).t == curVase || pedestals.get(pedestalIdx).r == curVase) {
                    res[vases.get(i).r] = pedestals.get(pedestalIdx++).u + 1;
                    break;
                }
                pedestalIdx++;
            }
        }

        if (res.length != 0 && Arrays.stream(res).min().getAsInt() <= 0)
            System.out.println("impossible");
        else
            System.out.print(Arrays.stream(res).mapToObj(String::valueOf).collect(Collectors.joining("\n")));
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

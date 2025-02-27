package org.zzamba.p27988;

import java.io.*;
import java.util.*;

// 더 빠른 풀이 존재 (O(N) 풀이)
public class Main {
    static int N;
    static int[][] inputs;
    static List<Pair<Integer, Integer>> Rs, Ys, Bs;
    static Pair<Integer, Integer> res;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        res = null;
        Rs = new ArrayList<>(N);
        Ys = new ArrayList<>(N);
        Bs = new ArrayList<>(N);
        N = Integer.parseInt(br.readLine());
        inputs = new int[2][N];
        inputs[0] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        inputs[1] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        String[] colors = br.readLine().split(" ");

		for (int i = 0; i < N; i++) {
			Pair<Integer, Integer> p = new Pair<>(inputs[0][i] - inputs[1][i], i);

			switch (colors[i].charAt(0)) {
				case 'R' -> Rs.add(p);
				case 'Y' -> Ys.add(p);
				case 'B' -> Bs.add(p);
			}
		}
		Rs.sort(Comparator.comparing(Pair::getT));
        Ys.sort(Comparator.comparing(Pair::getT));
        Bs.sort(Comparator.comparing(Pair::getT));

        for (Pair<Integer, Integer> p : Rs) {
            if (res != null)
                break;
            res = find(p, Ys, Bs);
        }
        for (Pair<Integer, Integer> p : Ys) {
            if (res != null)
                break;
            res = find(p, Rs, Bs);
        }
        for (Pair<Integer, Integer> p : Bs) {
            if (res != null)
                break;
            res = find(p, Rs, Ys);
        }

        if (res == null)
            System.out.println("NO");
        else {
            System.out.println("YES");
            System.out.println(res.t + " " + res.r);
        }

    }

    static Pair<Integer, Integer> find(Pair<Integer, Integer> p, List<Pair<Integer, Integer>> c1, List<Pair<Integer, Integer>> c2) {
        Pair<Integer, Integer> ret = binarySearch(p, c1);

        if (ret == null)
            ret = binarySearch(p, c2);

        return ret;
    }

    private static Pair<Integer, Integer> binarySearch(Pair<Integer, Integer> p, List<Pair<Integer, Integer>> comp) {
        int s = 0, e = comp.size() - 1;
        int val = inputs[1][p.r] + inputs[0][p.r];
        while (s <= e) {
            int mid = (s + e) / 2;

            if (comp.get(mid).t > val)
                e = mid - 1;
            else
                s = mid + 1;
        }

        if (e >= 0 && comp.get(e).r > p.r && val >= comp.get(e).t)
            return new Pair<>(p.r + 1, comp.get(e).r + 1);
        if (e + 1 < comp.size() && comp.get(e + 1).r > p.r && val >= comp.get(e + 1).t)
            return new Pair<>(p.r + 1, comp.get(e + 1).r + 1);

        return null;
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

package org.zzamba.p1826;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, L, P;
    static List<Pair<Integer, Integer>> charges; // { 위치, 연료통 수 }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        charges = IntStream.range(0, N).mapToObj(i -> new Pair<>(sc.nextInt(), sc.nextInt())).collect(Collectors.toList());
        charges.sort(Comparator.comparing(Pair::getT));
        L = sc.nextInt();
        P = sc.nextInt();
        charges.add(new Pair<>(L, 0));

        System.out.println(cal());
    }

    static int cal() {
        int ret = 0;
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.<Pair<Integer, Integer>, Integer>comparing(Pair::getR).reversed());
        int curLoc = 0;

        for (Pair<Integer, Integer> p : charges) {
			if (!canReach(curLoc, p.t)) {
				while (!pq.isEmpty() && !canReach(curLoc, p.t)) {
                    P += pq.poll().r;
                    ret++;
                }

				if (!canReach(curLoc, p.t))
					return -1;
			}

			pq.add(p);
            P -= p.t - curLoc;
			curLoc = p.t;
		}

        return ret;
    }

    static boolean canReach(int curLoc, int next) {
        return next - curLoc <= P;
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

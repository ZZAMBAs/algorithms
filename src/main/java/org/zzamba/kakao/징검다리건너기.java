package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

class Solution {
	public int solution(int[] stones, int k) {
		int answer;
		List<Pair<Integer, Boolean>> pairs = new ArrayList<>(); // val, isAlive
		PriorityQueue<Pair<Integer, Boolean>> pq = new PriorityQueue<>(Comparator.comparing(Pair<Integer, Boolean>::getT).reversed());

		IntStream.range(0, k).forEach(i -> {
			Pair<Integer, Boolean> p = new Pair<>(stones[i], true);
			pq.add(p);
			pairs.add(p);
		});
		answer = pq.peek().t;

		int s = 0, e = k - 1;
		while (e + 1 < stones.length) {
			pairs.get(s++).r = false;
			pairs.add(new Pair<>(stones[++e], true));
			pq.add(pairs.get(e));

			while (!pq.peek().r)
				pq.poll();

			answer = Math.min(answer, pq.peek().t);
		}

		return answer;
	}

	static class Pair<T, R> {
		T t;
		R r;
		Pair (T t, R r) {
			this.t = t;
			this.r = r;
		}

		T getT() {
			return t;
		}
	}
}

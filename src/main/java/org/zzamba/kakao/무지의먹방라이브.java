package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

// 정렬로도 풀이 가능함
class Solution {
	public int solution(int[] food_times, long k) {
		int answer = -1;
		PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getT)); // {값, idx}
		IntStream.range(0, food_times.length).forEach(idx -> pq.add(new Pair<>(food_times[idx], idx)));
		long curK = 0L; // 현재 지난 시간
		int curV = 0; // 각각 먹은 횟수

		while (!pq.isEmpty()) {

			int pqSize = pq.size();
			Pair<Integer, Integer> p = pq.poll();
			long diff = p.t - curV;

			if (curK + diff * pqSize <= k) {
				curK += diff * pqSize;
				curV = p.t;
				food_times[p.r] = 0;
				continue;
			}

			curK += binarySearch(curK, k, pqSize);

			for (int i = 0; i < food_times.length; i++) {
				if (food_times[i] == 0)
					continue;

				if (k - curK == 0)
					return i + 1;

				curK++;
			}
		}

		return answer;
	}

	long binarySearch(long curK, long k, int pqSize) {
		long s = 0;
		long e = 100000000;

		while (s <= e) {
			long mid = (s + e) / 2;

			if (curK + mid * pqSize <= k)
				s = mid + 1;
			else
				e = mid - 1;
		}

		return e * pqSize;
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
	}
}

package org.zzamba.kakao;

import java.util.*;

class Solution {
	static int staticN;
	static int answer = Integer.MAX_VALUE;
	static int[] sortedWeak;
	static int[] sortedDist;

	public int solution(int n, int[] weak, int[] dist) {
		staticN = n;
		sortedWeak = weak;
		sortedDist = Arrays.stream(dist).map(d -> Math.min(n - 1, d)).sorted().toArray();

		for (int i = 1; i <= Math.min(sortedWeak.length, sortedDist.length) && answer == Integer.MAX_VALUE; i++)
			bruteforce(sortedWeak.length, i, 0, new ArrayList<>());

		return answer == Integer.MAX_VALUE ? -1 : answer;
	}

	void bruteforce(int rest, int limit, int depth, List<Integer> list) {
		if (depth == limit - 1) {
			list.add(rest);
			check(list);
			list.remove(list.size() - 1);
			return;
		}

		for (int i = 1; i <= rest - limit + depth + 1; i++) {
			list.add(i);
			bruteforce(rest - i, limit, depth + 1, list);
			list.remove(list.size() - 1);
		}
	}

	void check(List<Integer> list) {
		for (int i = 0; i < sortedWeak.length; i++) {
			int curPtr = i;
			PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
			int distPtr = sortedDist.length - 1;

			for (int len : list) {
				int s = curPtr;
				curPtr = (curPtr + len) % sortedWeak.length;
				int e = (curPtr + sortedWeak.length - 1) % sortedWeak.length;

				pq.add(calDist(s, e));
			}

			while (!pq.isEmpty()) {
				if (pq.peek() > sortedDist[distPtr])
					break;
				pq.poll();
				distPtr--;
			}

			if (pq.isEmpty())
				answer = Math.min(answer, list.size());
		}
	}

	int calDist(int idx1, int idx2) {
		if (idx1 <= idx2)
			return sortedWeak[idx2] - sortedWeak[idx1];
		return sortedWeak[idx2] + (staticN - sortedWeak[idx1]);
	}
}

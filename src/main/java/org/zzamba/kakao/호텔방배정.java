package org.zzamba.kakao;

import java.util.*;

// +1 개념만 이용한다면 굳이 union-find를 사용하지 않아도 된다.
class Solution {
	Map<Long, Long> par = new HashMap<>();

	public long[] solution(long k, long[] room_number) {
		List<Long> ans = new ArrayList<>();

		Arrays.stream(room_number).forEach(num -> {
			if (par.get(num) == null) {
				par.put(num, num);
				merge(num - 1, num);
				merge(num, num + 1);

				ans.add(num);
			} else {
				long p = find(num) + 1;

				par.put(p, p);
				merge(p - 1, p);
				merge(p, p + 1);

				ans.add(p);
			}
		});

		return ans.stream()
			.mapToLong(a -> a)
			.toArray();
	}

	long find(long idx) {
		if (par.get(idx) == null)
			return -1L;

		if (par.get(idx) == idx)
			return idx;

		par.put(idx, find(par.get(idx)));
		return par.get(idx);
	}

	void merge(long idx1, long idx2) {
		if (idx1 > idx2) {
			long temp = idx1;
			idx1 = idx2;
			idx2 = temp;
		}

		if (par.get(idx1) == null || par.get(idx2) == null)
			return;

		long par1 = find(idx1);
		long par2 = find(idx2);

		//System.out.println("par1: " + par1 + ", par2: " + par2);

		if (par1 == par2)
			return;

		par.put(par1, par2);
		//System.out.println("after par1: " + par.get(par1));
	}
}

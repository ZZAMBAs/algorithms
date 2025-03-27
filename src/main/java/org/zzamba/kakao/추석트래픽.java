package org.zzamba.kakao;

import java.util.*;

class Solution {
	public int solution(String[] lines) {
		int answer = 0;
		List<Pair<Integer, Boolean>> list = new ArrayList<>(); // {time, isStart}

		Arrays.stream(lines).forEach(str -> {
			Pair<Integer, Integer> parsed = parse(str);
			list.add(new Pair<>(parsed.t, true));
			list.add(new Pair<>(parsed.r, false));
		});
		list.sort(Comparator.comparing(Pair::getT));

		int curCnt = 0;
		int s = 0, e = 0;
		while (e < list.size()) {
			//System.out.println("s: " + s + ", e: " + e + ", curCnt: " + curCnt);
			while (e < list.size() && list.get(e).t - list.get(s).t < 1000) {
				//System.out.println(list.get(e).t);
				if (list.get(e).r)
					curCnt++;
				e++;
			}
			//System.out.println("e: " + e + ", curCnt: " + curCnt);

			answer = Math.max(answer, curCnt);

			if (!list.get(s++).r)
				curCnt--;
		}
		answer = Math.max(answer, curCnt);

		return answer;
	}

	Pair<Integer, Integer> parse(String str) {
		String[] split = str.split(" ");

		String[] mid = split[1].split(":");
		String[] secs = mid[2].split("\\.");

		int end = Integer.parseInt(mid[0]) * 60 * 60 * 1000 +
			Integer.parseInt(mid[1]) * 60 * 1000 +
			Integer.parseInt(secs[0]) * 1000 +
			Integer.parseInt(secs[1]);

		int diff = 0;
		int idx = 0;
		int mul = 1000;
		boolean sw = false;
		while (split[2].charAt(idx) != 's') {
			if (sw)
				mul /= 10;

			if (split[2].charAt(idx) == '.')
				sw = true;
			else
				diff += (split[2].charAt(idx) - 48) * mul;

			idx++;
		}

		int start = end - diff + 1;

		//System.out.println("start: " + start + ", end: " + end);

		return new Pair<>(start, end);
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

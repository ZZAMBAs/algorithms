package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

class Solution {
	public int[] solution(String[] words, String[] queries) {
		List<Integer> answer = new ArrayList<>();
		List<List<String>> pNormalList = new ArrayList<>();
		List<List<String>> pReverseList = new ArrayList<>();
		IntStream.range(0, 10001).forEach(i -> {
			pNormalList.add(new ArrayList<>());
			pReverseList.add(new ArrayList<>());
		});
		Arrays.stream(words).distinct().forEach(w -> {
			int len = w.length();
			pNormalList.get(len).add(w);

			StringBuilder sb = new StringBuilder();
			for (int i = len - 1; i >= 0; i--)
				sb.append(w.charAt(i));
			pReverseList.get(len).add(sb.toString());
		});
		pNormalList.forEach(l -> l.sort(Comparator.naturalOrder()));
		pReverseList.forEach(l -> l.sort(Comparator.naturalOrder()));

		Arrays.stream(queries).forEach(q -> {
			StringBuilder sb = new StringBuilder();
			List<String> list;
			int cnt = 0;

			if (q.charAt(0) == '?') {
				for (int i = q.length() - 1; i >= 0; i--) {
					if (q.charAt(i) == '?')
						break;
					sb.append(q.charAt(i));
					cnt++;
				}
				list = pReverseList.get(q.length());
			} else {
				for (int i = 0; i < q.length(); i++) {
					if (q.charAt(i) == '?')
						break;
					sb.append(q.charAt(i));
					cnt++;
				}
				list = pNormalList.get(q.length());
			}

			int s = binaryL(sb + "a".repeat(q.length() - cnt), list);
			int e = binaryR(sb + "z".repeat(q.length() - cnt), list);

			answer.add(e - s);
		});

		return answer.stream().mapToInt(v -> v).toArray();
	}

	int binaryL(String find, List<String> list) {
		int s = 0;
		int e = list.size() - 1;

		while (s <= e) {
			int mid = (s + e) / 2;

			if (find.compareTo(list.get(mid)) <= 0)
				e = mid - 1;
			else
				s = mid + 1;
		}

		return s;
	}

	int binaryR(String find, List<String> list) {
		int s = 0;
		int e = list.size() - 1;

		while (s <= e) {
			int mid = (s + e) / 2;

			if (find.compareTo(list.get(mid)) >= 0)
				s = mid + 1;
			else
				e = mid - 1;
		}

		return s;
	}
}
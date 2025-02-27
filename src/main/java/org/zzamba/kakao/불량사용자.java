package org.zzamba.kakao;

import java.util.*;

// https://school.programmers.co.kr/learn/courses/30/lessons/64064
class Solution {
	static int res = 0;
	static List<Integer> selectedIdx = new ArrayList<>();

	public int solution(String[] user_id, String[] banned_id) {
		int answer = 0;

		comb(0, 0, banned_id.length, user_id, banned_id);

		answer = res;
		return answer;
	}

	void comb(int idx, int cnt, int limit, String[] user_id, String[] banned_id) {
		if (cnt == limit) {
			res += validate(user_id, banned_id) ? 1 : 0;
			return;
		}

		for (int i = idx; i <= user_id.length - (limit - cnt); i++) {
			selectedIdx.add(i);
			comb(i + 1, cnt + 1, limit, user_id, banned_id);
			selectedIdx.remove(selectedIdx.size() - 1);
		}
	}

	boolean validate(String[] user_id, String[] banned_id) {
		return permutation(0, user_id, banned_id, new boolean[user_id.length], new int[banned_id.length]);
	}

	boolean permutation(int idx, String[] user_id, String[] banned_id, boolean[] visited, int[] perIdx) {
		boolean ret = false;

		if (idx == selectedIdx.size()) {
			ret = true;

			for (int k = 0; k < selectedIdx.size(); k++)
				ret = ret & check(user_id[perIdx[k]], banned_id[k]);

			return ret;
		}

		for (int i = 0; i < selectedIdx.size(); i++) {
			if (visited[selectedIdx.get(i)])
				continue;

			perIdx[idx] = selectedIdx.get(i);
			visited[selectedIdx.get(i)] = true;
			ret = ret | permutation(idx + 1, user_id, banned_id, visited, perIdx);
			visited[selectedIdx.get(i)] = false;
		}

		return ret;
	}

	boolean check(String a, String b) {
		if (a.length() != b.length())
			return false;

		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i) && b.charAt(i) != '*')
				return false;
		}

		return true;
	}
}

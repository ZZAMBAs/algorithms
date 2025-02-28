package org.zzamba.kakao;

import java.util.*;

class Solution {
	static int[] answer = new int[2];
	static int len = Integer.MAX_VALUE;

	public int[] solution(String[] gems) {
		Map<String, Integer> map = new HashMap<>();
		int[] convertedGems = new int[gems.length];
		int idx = 0;
		for (int i = 0; i < gems.length; i++) {
			String gem = gems[i];

			Integer converted = map.get(gem);
			if (converted != null) {
				convertedGems[i] = converted;
				continue;
			}

			map.put(gem, idx++);
			convertedGems[i] = map.get(gem);
		}

		twoPointer(convertedGems, idx);

		return answer;
	}

	public void twoPointer(int[] convertedGems, int allMatchCnt) {
		int s = 0, e = 0, cnt = 0;
		int[] visited = new int[allMatchCnt];

		while (s <= e && s < convertedGems.length) {
			if (e < convertedGems.length && (cnt < allMatchCnt || (e - s) == cnt)) {
				int eVal = convertedGems[e];

				if (visited[eVal] == 0)
					cnt++;
				visited[eVal]++;
				e++;
			} else {
				int sVal = convertedGems[s];
				visited[sVal]--;
				if (visited[sVal] == 0)
					cnt--;
				s++;
			}

			if (cnt == allMatchCnt && len > e - s) {
				len = e - s;
				answer = new int[]{s + 1, e};
			}
		}
	}
}

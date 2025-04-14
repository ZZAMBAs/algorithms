package org.zzamba.kakao;

import java.util.*;

// Trie로 풀었지만 정렬로도 풀이가 가능하다.
// https://tech.kakao.com/posts/348
class Solution {
	HashMap<Character, Trie> gTrie = new HashMap<>();

	public int solution(String[] words) {
		int answer = 0;

		Arrays.stream(words).forEach(str -> {
			HashMap<Character, Trie> curM = gTrie;
			for (int i = 0; i < str.length(); i++) {
				curM.putIfAbsent(str.charAt(i), new Trie());
				Trie curT = curM.get(str.charAt(i));
				curM = curT.next;
				curT.cnt++;
			}
		});

		for (String word : words) {
			HashMap<Character, Trie> curM = gTrie;
			for (int i = 0; i < word.length(); i++) {
				answer++;
				Trie curT = curM.get(word.charAt(i));

				if (curT.cnt == 1)
					break;

				curM = curT.next;
			}
		}

		return answer;
	}

	static class Trie {
		int cnt;
		HashMap<Character, Trie> next;

		Trie () {
			this.cnt = 0;
			this.next = new HashMap<>();
		}
	}
}

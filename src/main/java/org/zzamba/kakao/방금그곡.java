package org.zzamba.kakao;

import java.util.*;

class Solution {
	static int curIdx = 0;

	public String solution(String m, String[] musicinfos) {
		String answer;

		String convertedM = convert(m);
		MusicInfo[] res = Arrays.stream(musicinfos).map(this::parseAndGet)
			.filter(info -> info.hasMelody(convertedM))
			.sorted()
			.toArray(MusicInfo[]::new);

		if (res.length == 0)
			answer = "(None)";
		else
			answer = res[0].name;

		return answer;
	}

	String convert(String melody) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < melody.length(); i++) {
			int curV = (melody.charAt(i) - 65) * 2;

			if (i + 1 < melody.length() && melody.charAt(i + 1) == '#') {
				i++;
				curV++;
			}

			sb.append((char)curV);
		}

		return sb.toString();
	}

	MusicInfo parseAndGet(String input) {
		String[] split = input.split(",");

		int s = getSeconds(split[0]);
		int e = getSeconds(split[1]);

		if (e < s)
			e += 3600;

		StringBuilder sb = new StringBuilder();
		String convertedM = convert(split[3]);

		for (int i = 0; i < e - s; i++)
			sb.append(convertedM.charAt(i % convertedM.length()));

		return new MusicInfo(s, e, curIdx++, sb.toString(), split[2]);
	}

	int getSeconds(String time) {
		String[] t = time.split(":");
		return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
	}

	static class MusicInfo implements Comparable<MusicInfo> {
		int startTime;
		int endTime;
		int idx;
		String melody;
		String name;

		MusicInfo(int s, int e, int idx, String m, String n) {
			this.startTime = s;
			this.endTime = e;
			this.melody = m;
			this.name = n;
			this.idx = idx;
		}

		boolean hasMelody(String m) {
			//System.out.println("m: " + m + ", melody: " + melody +", name: " + name + ", hasMelody: " + this.melody.contains(m));
			return this.melody.contains(m);
		}

		int getPlayTime() {
			return this.endTime - this.startTime;
		}

		@Override
		public int compareTo(MusicInfo o) {
			if (this.getPlayTime() != o.getPlayTime()) {
				return this.getPlayTime() > o.getPlayTime() ? -1 : 1;
			}

			return this.idx < o.idx ? -1 : 1;
		}
	}
}
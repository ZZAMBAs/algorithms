package org.zzamba.kakao;

import java.util.*;

class Solution {
	static List<int[]> answer = new ArrayList<>();
	static int[][] map; // 01: 보, 10: 기둥, 11:보 + 기둥

	public int[][] solution(int n, int[][] build_frame) { // {col, row, type, create_or_delete}
		map = new int[n + 1][n + 1];

		Arrays.stream(build_frame).forEach(frame -> {
			switch(frame[3]) {
				case 1 -> create(frame);
				case 0 -> delete(frame);
			}
		});

		int resIdx = 0;
		for (int i = 0; i < n + 1; i++)
			for (int j = 0; j < n + 1; j++) {
				if ((map[j][i] & 0b10) == 0b10)
					answer.add(new int[]{i, j, 0});

				if ((map[j][i] & 1) == 1)
					answer.add(new int[]{i, j, 1});
			}
		int[][] ans = answer.stream().toArray(int[][]::new);

		return ans;
	}

	void create(int[] frame) {
		int row = frame[1];
		int col = frame[0];
		int type = frame[2];

		map[row][col] |= (type == 1) ? 1 : 0b10;

		boolean chk = check(row, col);

		if (!chk)
			map[row][col] &= (type == 1) ? 0b10 : 1;
	}

	void delete(int[] frame) {
		int row = frame[1];
		int col = frame[0];
		int type = frame[2];

		map[row][col] &= (type == 1) ? 0b10 : 1;

		boolean chk = true;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (inRange(row - i, col - j))
					chk &= check(row - i, col - j);

		if (!chk)
			map[row][col] |= (type == 1) ? 1 : 0b10;
	}

	boolean inRange(int r, int c) {
		return r >= 0 && r < map.length && c >= 0 && c < map.length;
	}

	boolean check(int r, int c) {
		boolean ret1 = true;
		boolean ret2 = true;

		if ((map[r][c] & 1) == 1) { // 보가 있음
			ret1 = false;

			if (inRange(r - 1, c))
				ret1 |= (map[r - 1][c] & 0b10) > 0;
			if (inRange(r - 1, c + 1))
				ret1 |= (map[r - 1][c + 1] & 0b10) > 0;
			if (inRange(r, c - 1) && inRange(r, c + 1))
				ret1 |= (map[r][c - 1] & 1) == 1 && (map[r][c + 1] & 1) == 1;
		}

		if ((map[r][c] & 0b10) > 0) { // 기둥 있음
			ret2 = false;

			ret2 |= r == 0;
			if (inRange(r - 1, c))
				ret2 |= (map[r - 1][c] & 0b10) > 0;
			ret2 |= (map[r][c] & 1) == 1;
			if (inRange(r, c - 1))
				ret2 |= (map[r][c - 1] & 1) == 1;
		}

		return ret1 && ret2;
	}
}

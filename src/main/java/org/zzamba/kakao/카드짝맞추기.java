package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

// 기존 완탐 + BFS에 DP를 추가로 얹을 수 있다고 한다.
// https://blog.encrypted.gg/996
class Solution {
	static final int[][] DL = new int[][]{ {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

	static int answer = Integer.MAX_VALUE;
	static boolean[] visited = new boolean[7]; // 순서용
	static boolean[] passed = new boolean[7]; // 뒤집었는지 확인용
	static int initR, initC;
	static List<List<Integer>> cardCoor = new ArrayList<>();
	static int[][] map;

	public int solution(int[][] board, int r, int c) {
		map = board;
		IntStream.range(0, 7).forEach(i -> cardCoor.add(new ArrayList<>()));
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				if (board[row][col] != 0) {
					List<Integer> cur = cardCoor.get(board[row][col]);
					cur.add(row);
					cur.add(col);
				}

		initR = r;
		initC = c;
		bruteforce(0, new ArrayList<>());

		return answer;
	}

	void bruteforce(int idx, List<Integer> p) {
		if (idx == 6) {
			check(0, initR, initC, p, 0);
			return;
		}

		for (int i = 1; i <= 6; i++) {
			if (visited[i])
				continue;

			visited[i] = true;
			p.add(i);
			bruteforce(idx + 1, p);
			p.remove(p.size() - 1);
			visited[i] = false;
		}

	}

	void check(int idx, int curR, int curC, List<Integer> p, int sum) {
		if (idx == 6) {
			answer = Math.min(answer, sum);
			return;
		}


		List<Integer> coor = cardCoor.get(p.get(idx));

		if (coor.isEmpty()) {
			check(idx + 1, curR, curC, p, sum);
			return;
		}

		// 첫 카드 -> 다음 카드
		int nextSum = bfs(curR, curC, coor.get(0), coor.get(1)) +
			bfs(coor.get(0), coor.get(1), coor.get(2), coor.get(3)) + 2;

		passed[p.get(idx)] = true;
		check(idx + 1, coor.get(2), coor.get(3), p, sum + nextSum);
		passed[p.get(idx)] = false;

		// 다음 카드 -> 첫 카드
		nextSum = bfs(curR, curC, coor.get(2), coor.get(3)) +
			bfs(coor.get(2), coor.get(3), coor.get(0), coor.get(1)) + 2;

		passed[p.get(idx)] = true;
		check(idx + 1, coor.get(0), coor.get(1), p, sum + nextSum);
		passed[p.get(idx)] = false;
	}

	int bfs(int r, int c, int destR, int destC) {
		int level = 0;
		boolean[][] v = new boolean[4][4];
		Queue<Pair<Integer, Integer>> q = new ArrayDeque<>();
		q.add(new Pair<>(r, c));
		v[r][c] = true;

		while (!q.isEmpty()) {
			int qSize = q.size();
			for (int i = 0; i < qSize; i++) {
				Pair<Integer, Integer> top = q.poll();
				int curR = top.t;
				int curC = top.r;

				if (curR == destR && curC == destC)
					return level;

				for (int[] d : DL) {
					int nextR = curR + d[0];
					int nextC = curC + d[1];

					if (inRange(nextR, nextC) && !v[nextR][nextC]) {
						v[nextR][nextC] = true;
						q.add(new Pair<>(nextR, nextC));
					}

					while (true) {
						if (!inRange(nextR, nextC)) {
							nextR = nextR - d[0];
							nextC = nextC - d[1];
							break;
						}

						if (map[nextR][nextC] != 0 && !passed[map[nextR][nextC]])
							break;

						nextR = nextR + d[0];
						nextC = nextC + d[1];
					}

					if (!v[nextR][nextC]) {
						v[nextR][nextC] = true;
						q.add(new Pair<>(nextR, nextC));
					}
				}
			}
			level++;
		}

		return -1000000;
	}

	boolean inRange(int r, int c) {
		return r >= 0 && r < 4 && c >= 0 && c < 4;
	}

	static class Pair<T, R> {
		T t;
		R r;

		Pair(T t, R r) {
			this.t = t;
			this.r = r;
		}
	}
}

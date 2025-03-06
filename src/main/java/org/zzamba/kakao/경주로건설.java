package org.zzamba.kakao;

import java.util.*;

class Solution {
	static final int WALL = 1;
	static final int LINEAR_COST = 100;
	static final int CORNER_COST = 500;
	static final int[][] DL = { {0, 1}, {1, 0}, {0, -1}, {-1, 0}};

	static int[][][] dist;

	public int solution(int[][] board) {

		dist = new int[2][25][25]; // 방향, 행, 열

		PriorityQueue<Quad<Integer, Integer, Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparing(Quad::getT));

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 25; j++)
				Arrays.fill(dist[i][j], Integer.MAX_VALUE);

		if (board.length > 1 && board[1][0] != WALL) {
			dist[1][1][0] = LINEAR_COST;
			pq.add(new Quad<>(LINEAR_COST, 1, 0, 1));
		}
		if (board[0].length > 1 && board[0][1] != WALL) {
			dist[0][0][1] = LINEAR_COST;
			pq.add(new Quad<>(LINEAR_COST, 0, 1, 0));
		}

		while (!pq.isEmpty()) {
			Quad<Integer, Integer, Integer, Integer> cur = pq.poll();
			int curCost = cur.t;
			int curRow = cur.r;
			int curCol = cur.u;
			int curDir = cur.v;

			if (dist[curDir][curRow][curCol] < curCost)
				continue;

			for (int i = 0; i < DL.length; i++) {
				int[] curDl = DL[i];

				int nextR = curRow + curDl[0];
				int nextC = curCol + curDl[1];
				int nextCost = curCost + (i % 2 == curDir ? LINEAR_COST : LINEAR_COST + CORNER_COST);

				if (inRange(nextR, nextC, board.length, board[0].length) && dist[i % 2][nextR][nextC] > nextCost && board[nextR][nextC] != WALL) {
					dist[i % 2][nextR][nextC] = nextCost;
					pq.add(new Quad<>(nextCost, nextR, nextC, i % 2));
				}
			}
		}

		return Math.min(dist[0][board.length - 1][board[0].length - 1], dist[1][board.length - 1][board[0].length - 1]);
	}

	boolean inRange(int r, int c, int maxR, int maxC) {
		return r >= 0 && r < maxR && c >= 0 && c < maxC;
	}

	static class Quad<T, R, U, V> { // 비용, row, col, 방향
		T t;
		R r;
		U u;
		V v;
		Quad(T t, R r,U u, V v) {
			this.t = t;
			this.r = r;
			this.u = u;
			this.v = v;
		}

		T getT() {
			return t;
		}
	}
}

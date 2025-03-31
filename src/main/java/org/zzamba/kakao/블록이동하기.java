package org.zzamba.kakao;

import java.util.*;

class Solution {
	static boolean[][] verVisited, horVisited;
	static int[][] dl = new int[][]{ {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

	public int solution(int[][] board) {
		verVisited = new boolean[board.length][board.length];
		horVisited = new boolean[board.length][board.length];

		return bfs(board);
	}

	int bfs(int[][] board) {
		int depth = 0;
		Queue<Robot> q = new ArrayDeque<>();
		horVisited[0][0] = true;
		q.add(new Robot(0, 0, false));

		while (!q.isEmpty()) {
			int qSize = q.size();
			for (int i = 0; i < qSize; i++) {
				Robot curRobot = q.poll();

				if ((curRobot.isVertical && curRobot.row == board.length - 2 && curRobot.col == board.length - 1) || (!curRobot.isVertical && curRobot.row == board.length - 1 && curRobot.col == board.length - 2))
					return depth;

				for (int[] d : dl) {
					int nextR = curRobot.row + d[0];
					int nextC = curRobot.col + d[1];

					if (!inRange(nextR, nextC, curRobot.isVertical, board))
						continue;

					canReach(q, nextR, nextC, curRobot.isVertical, board);
				}

				allRotateAndPush(q, curRobot, board);
			}
			depth++;
		}

		return -1;
	}

	boolean inRange(int r, int c, boolean isVertical, int[][] board) {
		if (isVertical)
			return r >= 0 && r < board.length - 1 && c >= 0 && c < board.length;
		return r >= 0 && r < board.length && c >= 0 && c < board.length - 1;
	}

	void canReach(Queue<Robot> q, int row, int col, boolean isVertical, int[][] board) {
		if (board[row][col] == 1)
			return;

		if (isVertical) {
			if (verVisited[row][col] || board[row + 1][col] == 1)
				return;
			verVisited[row][col] = true;
		} else {
			if (horVisited[row][col] || board[row][col + 1] == 1)
				return;
			horVisited[row][col] = true;
		}

		q.add(new Robot(row, col, isVertical));
	}

	void allRotateAndPush(Queue<Robot> q, Robot robot, int[][] board) {
		int[][] dr; // {dr, dc, checkDr, checkDc}

		if (!robot.isVertical)
			dr = new int[][]{ {-1, 0, -1, 1}, {0, 0, 1, 1}, {0, 1, 1, 0}, {-1, 1, -1, 0} };
		else
			dr = new int[][]{ {0, 0, 1, 1}, {0, -1, 1, -1}, {1, 0, 0, 1}, {1, -1, 0, -1} };

		for (int[] d : dr) {
			int nextR = robot.row + d[0];
			int nextC = robot.col + d[1];

			if (!inRange(nextR, nextC, !robot.isVertical, board) || board[robot.row + d[2]][robot.col + d[3]] == 1)
				continue;

			canReach(q, nextR, nextC, !robot.isVertical, board);
		}
	}

	static class Robot {
		int row;
		int col;
		boolean isVertical;

		Robot(int row, int col, boolean isVertical) {
			this.row = row;
			this.col = col;
			this.isVertical = isVertical;
		}
	}
}

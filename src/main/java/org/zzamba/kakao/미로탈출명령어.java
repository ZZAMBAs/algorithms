package org.zzamba.kakao;

// 그리디 + 수학 풀이로 O(1) 풀이가 존재한다.
// 그리디 DFS로도 풀 수 있다.
class Solution {
	static String[][][] dp;

	public String solution(int n, int m, int x, int y, int r, int c, int k) {
		dp = new String[2][n + 2][m + 2];
		dp[0][x][y] = "";

		for (int dep = 0; dep < k; dep++)
			for (int row = 1; row <= n; row++)
				for (int col = 1; col <= m; col++)
					if (dp[dep % 2][row][col] != null)
						move(row, col, dep, r, c, k);

		return dp[k % 2][r][c] == null ? "impossible" : dp[k % 2][r][c];
	}

	void move(int row, int col, int dep, int r, int c, int k) {
		int nextDep = (dep + 1) % 2;
		String curStr = dp[dep % 2][row][col];
		for (Direction curDir : Direction.values()) {
			int nextR = row + curDir.dr;
			int nextC = col + curDir.dc;
			String nextStr = curStr + curDir.val;

			if (dp[nextDep][nextR][nextC] == null || dp[nextDep][nextR][nextC].length() < nextStr.length() || dp[nextDep][nextR][nextC].compareTo(nextStr) > 1)
				dp[nextDep][nextR][nextC] = nextStr;
		}
	}

	enum Direction {
		DOWN(1, 0, "d"),
		LEFT(0, -1, "l"),
		RIGHT(0, 1, "r"),
		UP(-1, 0, "u");

		int dr;
		int dc;
		String val;

		Direction(int dr, int dc, String val) {
			this.dr = dr;
			this.dc = dc;
			this.val = val;
		}
	}
}

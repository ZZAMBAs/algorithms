package org.zzamba.kakao;

class Solution {
	static final int MOD_NUM = 10007;

	static int[] dp;

	public int solution(int n, int[] tops) {
		dp = new int[2 * n + 1];
		dp[0] = dp[1] = 1;

		for (int i = 1; i < dp.length; i++) {
			dp[i] = (dp[i] + dp[i - 1]) % MOD_NUM;

			if (i + 1 < dp.length)
				dp[i + 1] = (dp[i + 1] + dp[i - 1]) % MOD_NUM;

			if (i % 2 == 1 && tops[i / 2] == 1)
				dp[i] = (dp[i] + dp[i - 1]) % MOD_NUM;
		}

		return dp[2 * n];
	}
}

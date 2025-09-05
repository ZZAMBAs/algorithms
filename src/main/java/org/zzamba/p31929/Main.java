package org.zzamba.p31929;

import java.util.*;
import java.util.stream.*;

public class Main {
	static int[][] dp;
	static int[] wins, loses;
	static int N, M, K;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		wins = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
		M = sc.nextInt();
		loses = IntStream.range(0, M).map(i -> sc.nextInt()).toArray();
		dp = new int[N + 1][M + 1];
		IntStream.rangeClosed(0, N).forEach(i -> Arrays.fill(dp[i], Integer.MIN_VALUE));
		K = sc.nextInt();

		fdp();

		System.out.print(dp[N][M]);
	}

	static void fdp() {
		dp[0][0] = 0;

		IntStream.rangeClosed(0, N).forEach(row ->
			IntStream.rangeClosed(0, M).forEach(col -> {
				if (row != N)
					dp[row + 1][col] = Math.max(dp[row + 1][col], dp[row][col] + wins[row]);
				if (col != M)
					dp[row][col + 1] = Math.max(dp[row][col + 1], calMin(dp[row][col], loses[col]));
			})
		);
	}

	static int calMin(int curV, int subVal) {
		int limit = ((curV / K - 1) + (curV % K > 0 ? 1 : 0)) * K;

		return Math.max(limit, curV - subVal);
	}
}

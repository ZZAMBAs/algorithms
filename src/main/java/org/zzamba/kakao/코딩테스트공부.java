package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

class Solution {
	static final int MAX_LIMIT = 181;

	static int destAlp, destCop;
	static int res = Integer.MAX_VALUE;
	static int[][] dp = new int[MAX_LIMIT][MAX_LIMIT]; // dp[알골력][코딩력] = 최소 시간

	public int solution(int alp, int cop, int[][] problems) {
		IntStream.range(0, MAX_LIMIT).forEach(i -> Arrays.fill(dp[i], Integer.MAX_VALUE));
		List<Problem> pbms = Arrays.stream(problems).map(arr -> new Problem(arr[0], arr[1], arr[2], arr[3], arr[4])).collect(Collectors.toList());
		dp[alp][cop] = 0;
		for (Problem p : pbms) {
			destAlp = Math.max(destAlp, p.alpReq);
			destCop = Math.max(destCop, p.copReq);
		}

		for (int ap = alp; ap <= MAX_LIMIT - 30; ap++) {
			for (int cp = cop; cp <= MAX_LIMIT - 30; cp++) {
				if (dp[ap][cp] == Integer.MAX_VALUE)
					continue;
				//System.out.println("ap: " + ap + ", cp: " + cp + ", dp[ap][cp]: " + dp[ap][cp]);

				dp[ap][cp + 1] = Math.min(dp[ap][cp + 1], dp[ap][cp] + 1);
				dp[ap + 1][cp] = Math.min(dp[ap + 1][cp], dp[ap][cp] + 1);

				//System.out.println("dp[ap][cp + 1]: " + dp[ap][cp + 1] + ", dp[ap + 1][cp]: " + dp[ap + 1][cp]);

				for (Problem p : pbms) {
					if (!p.canSolve(ap, cp))
						continue;

					int nextAp = Math.min(150, ap + p.alpRwd);
					int nextCp = Math.min(150, cp + p.copRwd);

					dp[nextAp][nextCp] = Math.min(dp[nextAp][nextCp], dp[ap][cp] + p.cost);
				}
			}
		}

		for (int ap = destAlp; ap < MAX_LIMIT; ap++)
			for (int cp = destCop; cp < MAX_LIMIT; cp++)
				res = Math.min(res, dp[ap][cp]);

		return res;
	}

	static class Problem {
		int alpReq;
		int copReq;
		int alpRwd;
		int copRwd;
		int cost;

		Problem(int alpReq, int copReq, int alpRwd, int copRwd, int cost) {
			this.alpReq = alpReq;
			this.copReq = copReq;
			this.alpRwd = alpRwd;
			this.copRwd = copRwd;
			this.cost = cost;
		}

		boolean canSolve(int curAlp, int curCop) {
			return curAlp >= this.alpReq && curCop >= this.copReq;
		}
	}
}

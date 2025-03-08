package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

// 효율은 다익스트라가 더 좋긴 하지만 V가 최대 200이라 그리 의미가 있지는 않음.
class Solution {
	static final int INF = 30000000;

	static int answer = Integer.MAX_VALUE;
	static int[][] dist;

	public int solution(int n, int s, int a, int b, int[][] fares) {
		dist = new int[n][n];
		IntStream.range(0, n).forEach(i -> Arrays.fill(dist[i], INF));
		IntStream.range(0, n).forEach(i -> dist[i][i] = 0);
		Arrays.stream(fares).forEach(arr -> {
			dist[arr[0] - 1][arr[1] - 1] = arr[2];
			dist[arr[1] - 1][arr[0] - 1] = arr[2];
		});

		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);

		IntStream.range(0, n).forEach(mid -> answer = Math.min(answer, dist[s - 1][mid] + dist[mid][a - 1] + dist[mid][b - 1]));

		return answer;
	}
}

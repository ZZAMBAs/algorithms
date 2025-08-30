package org.zzamba.p24429;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
	static final int[][] DL = new int[][]{ {0, 1}, {1, 0} };

	static int[][] dp, map;
	static List<int[]> path = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		dp  = new int[n][n];

		IntStream.range(0, n).forEach(i -> {
			try {
				String[] parts = br.readLine().split(" ");
				IntStream.range(0, n).forEach(j -> {
					map[i][j] = Integer.parseInt(parts[j]);
					dp[i][j]  = 0;
				});
			} catch (IOException e) {}
		});

		int k = Integer.parseInt(br.readLine());
		IntStream.range(0, k).forEach(i -> {
			try {
				String[] parts = br.readLine().split(" ");
				path.add(new int[]{
					Integer.parseInt(parts[0]) - 1,
					Integer.parseInt(parts[1]) - 1
				});
			} catch (IOException e) {}
		});

		path.add(new int[]{0, 0});
		path.add(new int[]{n - 1, n - 1});

		path.sort(Comparator.comparingInt((int[] arr) -> arr[0])
			.thenComparingInt(arr -> arr[1])
		);

		if (!canReach()) {
			System.out.print(-1);
			return;
		}

		dp[0][0] = map[0][0];

		IntStream.range(0, path.size() - 1).forEach(i ->
			fdp(path.get(i), path.get(i + 1))
		);

		System.out.println(dp[n - 1][n - 1]);
	}

	static boolean canReach() {
		return IntStream.range(0, path.size() - 1)
			.filter(i -> path.get(i + 1)[1] < path.get(i)[1])
			.findAny()
			.isEmpty();
	}

	static void fdp(int[] s, int[] e) {
		IntStream.rangeClosed(s[0], e[0]).forEach(r ->
			IntStream.rangeClosed(s[1], e[1]).forEach(c ->
				Arrays.stream(DL).forEach(d -> {
					int[] next = new int[] {r + d[0], c + d[1]};
					if (next[0] <= e[0] && next[1] <= e[1])
						dp[next[0]][next[1]] = Math.max(dp[next[0]][next[1]], dp[r][c] + map[next[0]][next[1]]);
				})
			)
		);
	}
}

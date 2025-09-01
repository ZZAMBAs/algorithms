package org.zzamba.p16681;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
	static int N, M, D, E;
	static int[] heights;
	static long[] upMin, downMin;
	static List<List<int[]>> adj = new ArrayList<>(); // {dest, weight}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] inp = br.readLine().split(" ");
		N = Integer.parseInt(inp[0]);
		M = Integer.parseInt(inp[1]);
		D = Integer.parseInt(inp[2]);
		E = Integer.parseInt(inp[3]);
		IntStream.rangeClosed(0, N).forEach(i -> adj.add(new ArrayList<>()));
		heights = new int[N + 1];
		int[] temp = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		IntStream.rangeClosed(1, N).forEach(i -> heights[i] = temp[i - 1]);
		IntStream.range(0, M).forEach(i -> {
			try {
				int[] val = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				adj.get(val[0]).add(new int[]{val[1], val[2]});
				adj.get(val[1]).add(new int[]{val[0], val[2]});
			} catch (IOException e) {}
		});

		upMin = dijkstra(1);
		downMin = dijkstra(N);
		OptionalLong res = getRes();

		System.out.print(res.isPresent() ? res.getAsLong() : "Impossible");
	}

	static long[] dijkstra(int start) {
		long[] dist = new long[N + 1];
		boolean[] isVisited = new boolean[N + 1];
		Arrays.fill(dist, Long.MAX_VALUE);
		dist[start] = 0;

		PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparing((long[] arr) -> arr[0])); // {dist, dest}
		pq.add(new long[]{0, start});

		while (!pq.isEmpty()) {
			long[] first = pq.poll();

			if (isVisited[(int)first[1]])
				continue;

			isVisited[(int)first[1]] = true;

			adj.get((int)first[1]).forEach(adv -> {
				if (heights[adv[0]] > heights[(int)first[1]] && dist[adv[0]] > first[0] + adv[1]) {
					pq.add(new long[]{first[0] + adv[1], adv[0]});
					dist[adv[0]] = first[0] + adv[1];
				}
			});
		}

		return dist;
	}

	static OptionalLong getRes() {
		return IntStream.range(1, N)
			.filter(i -> upMin[i] != Long.MAX_VALUE && downMin[i] != Long.MAX_VALUE)
			.mapToLong(i -> (long)E * heights[i] - upMin[i] * D - downMin[i] * D)
			.max();
	}
}

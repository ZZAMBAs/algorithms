package org.zzamba.p21606;

import java.util.*;
import java.util.stream.*;

public class Main {
	static long res;
	static boolean[] bfsVisited, isOutside;
	static List<List<Integer>> adj = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		String str = sc.next();
		bfsVisited = new boolean[N + 1];
		isOutside = new boolean[N + 1];
		IntStream.rangeClosed(0, N).forEach(i -> adj.add(new ArrayList<>()));
		IntStream.rangeClosed(1, N).forEach(i -> isOutside[i] = str.charAt(i - 1) == '0');
		IntStream.range(0, N - 1).forEach(i -> {
			int a = sc.nextInt();
			int b = sc.nextInt();

			adj.get(a).add(b);
			adj.get(b).add(a);
		});

		IntStream.rangeClosed(1, N).forEach(i -> {
			if (isOutside[i]) {
				if (!bfsVisited[i])
					bfs(i);
			}
			else
				countAdj(i);
		});

		System.out.print(res);
	}

	static void bfs(int v) {
		long inside = 0L;
		Queue<Integer> q = new ArrayDeque<>();
		bfsVisited[v] = true;
		q.add(v);
		while (!q.isEmpty()) {
			int front = q.poll();

			for (int adv : adj.get(front)) {
				if (!isOutside[adv]) {
					inside++;
					continue;
				}

				if (bfsVisited[adv])
					continue;

				bfsVisited[adv] = true;
				q.add(adv);
			}
		}

		res += inside * (inside - 1L);
	}

	static void countAdj(int v) {
		res += adj.get(v).stream()
			.filter(adv -> !isOutside[adv])
			.count();
	}
}

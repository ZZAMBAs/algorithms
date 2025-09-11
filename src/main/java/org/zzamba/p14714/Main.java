package org.zzamba.p14714;

import java.util.*;
import java.util.stream.*;

public class Main {
	static int N, A, B, DA, DB;
	static boolean[][][] isVisited;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		A = sc.nextInt() - 1;
		B = sc.nextInt() - 1;
		DA = sc.nextInt();
		DB = sc.nextInt();
		isVisited = new boolean[N][N][2];

		int val = bfs();

		System.out.print(val == -1 ? "Evil Galazy" : val);
	}

	static int bfs() {
		int level = 0;
		Queue<int[]> q = new ArrayDeque<>(); // {A, B, aTrun?(1:A, -1:B) }
		q.add(new int[]{A, B, 1});
		isVisited[A][B][0] = true;
		while (!q.isEmpty()) {
			int qSize = q.size();
			for (int i = 0; i < qSize; i++) {
				int[] first = q.poll();

				if (first[0] == first[1])
					return level;

				if (first[2] == 1) { // A 지목 차례
					int[] nextA = new int[]{(first[0] + DA) % N, (first[0] - DA + N) % N};

					for (int next : nextA) {
						if (!isVisited[next][first[1]][1]) {
							isVisited[next][first[1]][1] = true;
							q.add(new int[]{next, first[1], first[2] * -1});
						}
					}
				} else {
					int[] nextB = new int[]{(first[1] + DB) % N, (first[1] - DB + N) % N};

					for (int next : nextB) {
						if (!isVisited[first[0]][next][0]) {
							isVisited[first[0]][next][0] = true;
							q.add(new int[]{first[0], next, first[2] * -1});
						}
					}
				}
			}
			level++;
		}

		return -1;
	}
}

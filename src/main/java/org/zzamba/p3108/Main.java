package org.zzamba.p3108;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int N;
	static int[] par;
	static int[][] rectCoors; // { {r1, c1, r2, c2}, ... }
	static boolean firstPuNeeded = true;

	public static void main(String[] args) throws Exception {
		N = Integer.parseInt(br.readLine());
		par = IntStream.range(0, N).toArray();
		rectCoors = new int[N][4];

		for (int idx = 0; idx < N; idx++) {
			int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			rectCoors[idx] = inp;

			if (isThroughStart(rectCoors[idx]))
				firstPuNeeded = false;
		}

		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				if (find(i) == find(j))
					continue;

				if (canMoveDirectly(rectCoors[i], rectCoors[j]))
					union(i, j);

			}
		}
		for (int i = 0; i < N; i++)
			find(i);

		long divideNum = Arrays.stream(par).distinct().count() - 1;
		System.out.print(firstPuNeeded ? divideNum + 1 : divideNum);
	}

	static boolean canMoveDirectly(int[] rect1, int[] rect2) {
		if (rect1[0] == rect2[0] && rect1[1] == rect2[1] && rect1[2] == rect2[2] && rect1[3] == rect2[3])
			return true;

		return isThrough(new int[]{rect1[0], rect1[1], rect1[3]}, new int[]{rect2[0], rect2[2], rect2[1]}) ||
			isThrough(new int[]{rect1[0], rect1[1], rect1[3]}, new int[]{rect2[0], rect2[2], rect2[3]}) ||
			isThrough(new int[]{rect1[2], rect1[1], rect1[3]}, new int[]{rect2[0], rect2[2], rect2[1]}) ||
			isThrough(new int[]{rect1[2], rect1[1], rect1[3]}, new int[]{rect2[0], rect2[2], rect2[3]}) ||
			isThrough(new int[]{rect2[0], rect2[1], rect2[3]}, new int[]{rect1[0], rect1[2], rect1[1]}) ||
			isThrough(new int[]{rect2[0], rect2[1], rect2[3]}, new int[]{rect1[0], rect1[2], rect1[3]}) ||
			isThrough(new int[]{rect2[2], rect2[1], rect2[3]}, new int[]{rect1[0], rect1[2], rect1[1]}) ||
			isThrough(new int[]{rect2[2], rect2[1], rect2[3]}, new int[]{rect1[0], rect1[2], rect1[3]}); // 이 방법보다 더 효율적인 방법이 있다.
	}

	static boolean isThroughStart(int[] coor) {
		return (coor[0] == 0 && coor[1] <= 0 && coor[3] >= 0) ||
			(coor[2] == 0 && coor[1] <= 0 && coor[3] >= 0) ||
			(coor[1] == 0 && coor[0] >= 0 && coor[2] <= 0) ||
			(coor[3] == 0 && coor[0] >= 0 && coor[2] <= 0);
	}

	static boolean isThrough(int[] hLine, int[] vLine) { // hLine: {r, c1, c2}, vLine: {r1, r2, c}
		return (vLine[0] <= hLine[0] && hLine[0] <= vLine[1]) &&
			(hLine[1] <= vLine[2] && vLine[2] <= hLine[2]);
	}

	static int find(int idx) {
		if (par[idx] == idx)
			return par[idx];

		par[idx] = find(par[idx]);
		return par[idx];
	}

	static void union(int a, int b) {
		int parA = find(a);
		int parB = find(b);

		if (parA == parB)
			return;

		par[parB] = parA;
	}
}

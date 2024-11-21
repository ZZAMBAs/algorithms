package org.zzamba.p1414;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int N, res;
	static List<Triple> edges;
	static int[] par;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		par = new int[N];
		edges = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			par[i] = i;
			String s = sc.next();
			for (int j = 0; j < N; j++) {
				char c = s.charAt(j);

				int w;
				if (c == 48)
					continue;
				else if (c < 97)
					w = c - 65 + 27;
				else
					w = c - 97 + 1;

				res += w;
				edges.add(new Triple(i, j, w));
				edges.add(new Triple(j, i, w));
			}
		}
		edges.sort(Comparator.comparing(Triple::getW));

		makeMST();

		System.out.println(linked() ? res : -1);
	}

	static void makeMST() {
		edges.forEach(edge -> {
			if (find(edge.s) != find(edge.e)) {
				res -= edge.w;
				merge(edge.s, edge.e);
			}
		});
	}

	static boolean linked() {
		int p = find(0);

		for (int i = 1; i < N; i++) {
			if (p != find(i))
				return false;
		}

		return true;
	}

	static int find(int idx) {
		if (par[idx] == idx)
			return par[idx];

		par[idx] = find(par[idx]);
		return par[idx];
	}

	static void merge(int idx1, int idx2) {
		int par1 = find(idx1);
		int par2 = find(idx2);

		if (par1 == par2)
			return;

		par[par2] = par1;
	}

	static class Triple {
		int s;
		int e;
		int w;

		public Triple(int s, int e, int w) {
			this.s = s;
			this.e = e;
			this.w = w;
		}

		public int getW() {
			return w;
		}
	}
}

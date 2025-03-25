package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

// 명령이 최대 1000개이고 연산이 최대 2500번만 수행되므로 Union-Find 없이도 풀이가 가능하다고 한다. 이 풀이는 그냥 2차원 배열에서 다 해결.
class Solution {
	static final String EMPTY = "EMPTY";
	static String[][] docs = new String[51][51];
	static int[] pars = new int[2501];

	public String[] solution(String[] commands) {
		List<String> answer = new ArrayList<>();

		for (int i = 1; i < 51; i++)
			Arrays.fill(docs[i], EMPTY);
		IntStream.range(1, 2501).forEach(i -> pars[i] = i);

		Arrays.stream(commands).forEach(cmd -> {
			String[] vals = cmd.split(" ");

			if (vals[0].equals("UPDATE")) {
				if (vals.length == 4) {
					//System.out.println("---UPDATE1---");
					int r = Integer.parseInt(vals[1]);
					int c = Integer.parseInt(vals[2]);
					int idx = convertToIdx(r, c);
					int par = find(idx);

					updateAtIdx(par, vals[3]);
				} else {
					//System.out.println("---UPDATE2---");
					for (int i = 1; i < 51; i++)
						for (int j = 1; j < 51; j++) {
							if (docs[i][j].equals(vals[1])) {
								updateAtIdx(convertToIdx(i, j), vals[2]);
							}
						}
				}

				//debug();
			}

			if (vals[0].equals("MERGE")) {
				//System.out.println("---MERGE---");

				int idx1 = convertToIdx(Integer.parseInt(vals[1]), Integer.parseInt(vals[2]));
				int idx2 = convertToIdx(Integer.parseInt(vals[3]), Integer.parseInt(vals[4]));
				int par1 = find(idx1);
				int par2 = find(idx2);

				//System.out.println("값2: " + docs[(par2 - 1) / 50 + 1][(par2 - 1) % 50 + 1] + ", 값1: " + docs[(par1 - 1) / 50 + 1][(par1 - 1) % 50 + 1]);
				//System.out.println();

				if (!docs[(par2 - 1) / 50 + 1][(par2 - 1) % 50 + 1].equals(EMPTY) &&
					docs[(par1 - 1) / 50 + 1][(par1 - 1) % 50 + 1].equals(EMPTY)) {
					merge(par2, par1);
				} else {
					merge(par1, par2);
				}
			}

			if (vals[0].equals("UNMERGE")) {
				//System.out.println("---UNMERGE---");
				int par = find(convertToIdx(Integer.parseInt(vals[1]), Integer.parseInt(vals[2])));
				String val = docs[(par - 1) / 50 + 1][(par - 1) % 50 + 1];
				//System.out.println("par: " + par + ", val: " + val);
				//System.out.println();
				List<Integer> updateList = new ArrayList<>();

				IntStream.range(1, 2501).forEach(i -> {
					if (find(i) == par) {
						//System.out.println("i: " + i + ", find: " + find(i));
						updateList.add(i);
						updateAtIdx(i, EMPTY);
					}
				});
				updateList.forEach(l -> pars[l] = l);

				//System.out.println("-pars-");
				//System.out.println(pars[1] + " " + pars[2] + " " + pars[51] + " " + pars[52]);

				docs[Integer.parseInt(vals[1])][Integer.parseInt(vals[2])] = val;
				//debug();
			}

			if (vals[0].equals("PRINT")) {
				//System.out.println("---PRINT---");
				int par = find(convertToIdx(Integer.parseInt(vals[1]), Integer.parseInt(vals[2])));
				//System.out.println(par + "\n");

				answer.add(docs[(par - 1) / 50 + 1][(par - 1) % 50 + 1]);
			}
		});
        /*
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++)
                System.out.print(docs[i][j] + " ");
            System.out.println();
        }
        */

		String[] ans = answer.stream().toArray(String[]::new);
		return ans;
	}

	int find(int idx) {
		if (pars[idx] == idx)
			return idx;
		pars[idx] = find(pars[idx]);
		return pars[idx];
	}

	void merge(int idx1, int idx2) {
		int par1 = find(idx1);
		int par2 = find(idx2);

		//System.out.println(par2 + "가 " + par1 + "에게 흡수됨");
		pars[par2] = par1;
	}

	int convertToIdx(int r, int c) {
		return (r - 1) * 50 + c;
	}

	void updateAtIdx(int idx, String v) {
		docs[(idx - 1) / 50 + 1][(idx - 1) % 50 + 1] = v;
	}

	void debug() {
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++)
				System.out.print(docs[i][j] + " ");
			System.out.println();
		}

		System.out.println();
	}
}

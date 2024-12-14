package org.zzamba.p18119;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

// 비트마스킹 활용 풀이: https://www.acmicpc.net/source/86462068
public class Main {
    static int N, M, res;
    static int[] wordAlphaCnt, curWordAlphaCnt;
    static List<List<Integer>> relatedIdx;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String[] inp = br.readLine().split(" ");
        N = Integer.parseInt(inp[0]);
        M = Integer.parseInt(inp[1]);
        wordAlphaCnt = new int[N];
        curWordAlphaCnt = new int[N];
        res = N;
        relatedIdx = new ArrayList<>();
        IntStream.range(0, 26).forEach(i -> relatedIdx.add(new ArrayList<>()));
        IntStream.range(0, N).forEach(i -> {
			String word;
			try {
				word = br.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			boolean[] alpha = new boolean[26];

            for (int j = 0; j < word.length(); j++)
                alpha[word.charAt(j) - 97] = true;

            for (int j = 0; j < 26; j++) {
                if (alpha[j]) {
                    wordAlphaCnt[i]++;
                    curWordAlphaCnt[i]++;
                    relatedIdx.get(j).add(i);
                }
            }
        });

        for (int i = 0; i < M; i++) {
            inp = br.readLine().split(" ");
            int cmd = Integer.parseInt(inp[0]);
            int cIdx = inp[1].charAt(0) - 97;

            switch (cmd) {
                case 1 -> cal(1, cIdx, sb);
                case 2 -> cal(-1, cIdx, sb);
            }
        }

        System.out.print(sb);
    }

    static void cal(int add, int cIdx, StringBuilder sb) {
        for (int idx : relatedIdx.get(cIdx)) {
            boolean isSame = curWordAlphaCnt[idx] == wordAlphaCnt[idx];

            curWordAlphaCnt[idx] += add;

            if (curWordAlphaCnt[idx] == wordAlphaCnt[idx])
                res++;
            else if (isSame && curWordAlphaCnt[idx] != wordAlphaCnt[idx])
                res--;
        }

        sb.append(res).append("\n");
    }
}

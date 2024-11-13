package org.zzamba.p4419;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;

public class Main {
    static int n, maxCnt;
    static List<Queue<Integer>> selects;
    static int[] votes;
    static Map<Integer, String> idxToName;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        sb = new StringBuilder();
        maxCnt = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        idxToName = new HashMap<>();
        n = Integer.parseInt(br.readLine());
        votes = new int[n + 1];
        selects = new ArrayList<>();
        IntStream.rangeClosed(1, n).forEach(i -> {
			String name;
			try {
				name = br.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			idxToName.put(i, name);
        });

        String select;
        while ((select = br.readLine()) != null) {
            int[] inp = Arrays.stream(select.split(" ")).mapToInt(Integer::parseInt).toArray();
            Queue<Integer> q = new ArrayDeque<>();
            Arrays.stream(inp).forEach(q::add);

            selects.add(q);
        }

        printWinner();
    }

    static void printWinner() {
        int bound = selects.size() / 2 + ((selects.size() % 2) == 1 ? 1 : 0);
        boolean[] eliminated = new boolean[n + 1];
        boolean loop = false;

        do {
            if (loop) {
                int minVote = Integer.MAX_VALUE;
                List<Integer> eliminateCandidates = new ArrayList<>();
                for (int i = 1; i <= n; i++) {
                    if (eliminated[i])
                        continue;

                    if (votes[i] == minVote) {
                        eliminateCandidates.add(i);
                    } else if (votes[i] < minVote) {
                        minVote = votes[i];
                        eliminateCandidates = new ArrayList<>();
                        eliminateCandidates.add(i);
                    }
                }

                eliminateCandidates.forEach(i -> eliminated[i] = true);
            }

            for (Queue<Integer> q : selects) {
                int preSize = q.size();

                while (!q.isEmpty() && eliminated[q.peek()])
                    q.poll();

                if (q.isEmpty())
                    continue;

                if (preSize != q.size() || !loop) {
                    int vote = q.peek();
                    votes[vote]++;
                    maxCnt = Math.max(maxCnt, votes[vote]);
                }
            }
        } while (loop = !winnerSelected(bound, maxCnt, eliminated));

        for (int j = 1; j <= n; j++) {
            if (votes[j] < maxCnt)
                continue;

            sb.append(idxToName.get(j)).append('\n');
        }

        System.out.print(sb);
    }

    static boolean winnerSelected(int bound, int maxCnt, boolean[] eliminated) {
        int preV = votes[1];

        if (maxCnt < bound) {
            for (int i = 1; i <= n; i++) {
                if (!eliminated[i] && preV != votes[i])
                    return false;
            }
            return true;
        } else {
            for (int i = 1; i <= n; i++) {
                if (votes[i] == maxCnt)
                    return true;
            }
            return false;
        }
    }
}

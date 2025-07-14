package org.zzamba.p16169;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int maxRank;
    static List<List<Pair>> computerByRank = new ArrayList<>();
    static int[][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        IntStream.range(0, n).forEach(i -> computerByRank.add(new ArrayList<>()));
        IntStream.range(0, n).forEach(i -> {
            int rank = sc.nextInt() - 1;
            maxRank = Math.max(maxRank, rank);

            Pair p = new Pair(i, sc.nextInt());

            computerByRank.get(rank).add(p);
        });
        dp = new int[maxRank + 1][100];

        IntStream.range(0, maxRank + 1).forEach(i -> {
            List<Pair> curR = computerByRank.get(i);
            IntStream.range(0, curR.size()).forEach(j -> {
                Pair curP = curR.get(j);

                // 시동
                dp[i][j] += curP.runtime;

                // 전달
                if (i < maxRank) {
                    List<Pair> nextR = computerByRank.get(i + 1);
                    IntStream.range(0, nextR.size()).forEach(k -> {
                        int sendTime = (curP.idx - nextR.get(k).idx) * (curP.idx - nextR.get(k).idx);

                        dp[i + 1][k] = Math.max(dp[i + 1][k], dp[i][j] + sendTime);
                    });
                }

            });
        });

        System.out.print(Arrays.stream(dp[maxRank]).max().getAsInt());
    }

    public static class Pair {
        int idx;
        int runtime;

        public Pair(int idx, int runtime) {
            this.idx = idx;
            this.runtime = runtime;
        }
    }
}

package org.zzamba.p9470;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int T, K, M, P;
    static int[] dp;
    static List<List<Integer>> inDeg;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        T = sc.nextInt();
        while (T-- > 0) {
            K = sc.nextInt();
            M = sc.nextInt();
            P = sc.nextInt();
            dp = new int[M + 1];
            inDeg = new ArrayList<>();
            IntStream.range(0, M + 1).forEach(i -> inDeg.add(new ArrayList<>()));
            IntStream.range(0, P).forEach(i -> {
                int s = sc.nextInt();
                int d = sc.nextInt();

                inDeg.get(d).add(s);
            });

            sb.append(K).append(' ').append(getStrahler(M)).append('\n');
        }

        System.out.print(sb);
    }

    static int getStrahler(int idx) {
        if (dp[idx] != 0)
            return dp[idx];

        if (inDeg.get(idx).isEmpty()) {
            dp[idx] = 1;
            return 1;
        }

        List<Integer> preStrahlers = inDeg.get(idx).stream().map(Main::getStrahler).collect(Collectors.toList());

        int max = 0;
        int cnt = 0;
        for (int preStrahler : preStrahlers) {
            if (preStrahler > max) {
                max = preStrahler;
                cnt = 1;
            } else if (preStrahler == max)
                cnt++;
        }

        if (cnt == 1)
            dp[idx] = max;
        else
            dp[idx] = max + 1;

        return dp[idx];
    }
}

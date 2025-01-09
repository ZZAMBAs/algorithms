package org.zzamba.p17841;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static final String STR = "UNIST";
    static final int MOD_NUM = 1_000_000_007;

    static int N;
    static List<List<String>> cand;
    static int[] dp;
    static int[][] subStr;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        cand = new ArrayList<>();
        IntStream.range(0, STR.length()).forEach(i -> cand.add(new ArrayList<>()));
        dp = new int[STR.length() + 1];
        dp[0] = 1;
        subStr = new int[STR.length()][STR.length() + 1];
        N = sc.nextInt();
        IntStream.range(0, N).forEach(i -> {
            String inp = sc.next();

            int inpItr = 0;
            int start = -1;
            for (int j = 0; j < STR.length() && inpItr < inp.length(); j++) {
                if (inp.charAt(inpItr) == STR.charAt(j)) {
                    if (start < 0)
                        start = j;
                    inpItr++;
                }
                else if (start >= 0) {
                    break;
                }
            }

            for (int j = 0; j < inpItr; j++)
                dp[start + 1 + j] = (dp[start + 1 + j] + dp[start]) % MOD_NUM;
        });

        System.out.print(dp[STR.length()]);
    }
}

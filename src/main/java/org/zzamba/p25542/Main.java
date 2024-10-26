package org.zzamba.p25542;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, L;
    static String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String res;
    static List<String> names;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        L = sc.nextInt();
        names = IntStream.range(0, N).mapToObj(i -> sc.next()).collect(Collectors.toList());
        res = "CALL FRIEND";

        backtracking(new char[L], 0, new int[N]);

        System.out.print(res);
    }

    static void backtracking(char[] curStr, int idx, int[] cnt) {
        if (idx == L) {
            StringBuilder sb = new StringBuilder();
            for (char c : curStr)
                sb.append(c);
            res = sb.toString();
            return;
        }

        for (int i = 0; i < 26; i++) {
            char c = alphabets.charAt(i);
            curStr[idx] = c;
            List<Integer> plus = new ArrayList<>();

            for (int j = 0; j < N; j++) {
                String name = names.get(j);

                if (name.charAt(idx) != c)
                    plus.add(j);
            }

            boolean sw = false;
            for (int cntIdx : plus) {
                if (++cnt[cntIdx] > 1)
                    sw = true;
            }

            if (sw) {
                for (int curIdx : plus)
                    cnt[curIdx]--;
                continue;
            }

            backtracking(curStr, idx + 1, cnt);

            for (int curIdx : plus)
                cnt[curIdx]--;
        }
    }
}

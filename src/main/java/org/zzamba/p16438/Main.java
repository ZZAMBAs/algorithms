package org.zzamba.p16438;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final int DAYS = 7;
    static List<String> res;
    static String padding;
    static char[] c = {'A', 'B'};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = new ArrayList<>();
        int N = sc.nextInt();
        padding = "A".repeat(N - 1) + "B";

        cal(N);

        while (res.size() < DAYS)
            res.add(padding);

        System.out.print(res.stream().collect(Collectors.joining("\n")));
    }

    static void cal(int n) {
        Queue<Integer> q = new ArrayDeque<>();
        int half = n / 2;
        int rest = n - half;
        int maxV = Math.max(half, rest);
        boolean sw = true;
        q.add(half);
        q.add(rest);

        while (maxV >= 1 && sw) {
            if (maxV == 1)
                sw = false;

            int cIdx = 0;
            int qSize = q.size();
            String curStr = "";

            while (qSize-- > 0) {
                int cnt = q.poll();
                int curHalf = cnt / 2;
                int curRest = cnt - curHalf;
                maxV = Math.max(curHalf, curRest);

                for (int i = 0; i < cnt; i++)
                    curStr += c[cIdx];
                cIdx = (cIdx + 1) % c.length;

                if (curHalf > 0)
                    q.add(curHalf);
                if (curRest > 0)
                    q.add(curRest);
            }

            res.add(curStr);
        }
    }
}
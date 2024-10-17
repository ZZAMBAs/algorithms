package org.zzamba.p10427;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int t, n;
    static long res;

    static List<Integer> debt, prefixSum;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        t = sc.nextInt();

        while (t-- > 0) {
            res = 0L;
            n = sc.nextInt();

            debt = IntStream.range(0, n).map(i -> sc.nextInt()).sorted().boxed().collect(Collectors.toList());
            prefixSum = new ArrayList<>(); // get(i) : [0, i)까지 합
            prefixSum.add(0);
            debt.forEach(d -> prefixSum.add(prefixSum.get(prefixSum.size() - 1) + d));

            for (int i = 1; i <= n; i++) {
                res += findMinAdditionalDebt(i);
            }

            System.out.println(res);
        }
    }

    static int findMinAdditionalDebt(int size) {
        int ret = Integer.MAX_VALUE;

        for (int idx = debt.size() - 1; idx >= size - 1; idx--) {
            int d = debt.get(idx) * size;
            int sub = prefixSum.get(idx + 1) - prefixSum.get(idx - size + 1);

            ret = Math.min(ret, d - sub);
        }

        return ret;
    }
}

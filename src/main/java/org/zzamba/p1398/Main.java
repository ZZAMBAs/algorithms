package org.zzamba.p1398;

import java.util.*;

public class Main {
    static List<Long> coins = new ArrayList<>();
    static Map<Long, Long> dp = new HashMap<>();

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        dp.put(0L, 0L);
        int T = sc.nextInt();
        long mul = 1L;
        for (int k = 0; k <= 15; k++) {
            if (k % 2 == 0)
                coins.add(25L * mul);
            coins.add(mul);
            mul *= 10L;
        }
        coins.sort(Comparator.naturalOrder());

        while (T-- > 0) {
            long inp = sc.nextLong();
            sb.append(cal(inp)).append('\n');
        }

        System.out.print(sb);
    }

    static long cal(long inp) {
        if (dp.get(inp) != null)
            return dp.get(inp);

        int s = 0;
        int e = coins.size() - 1;

        while (s <= e) {
            int midIdx = (s + e) / 2;
            long midVal = coins.get(midIdx);

            if (midVal > inp)
                e = midIdx - 1;
            else
                s = midIdx + 1;
        }

        long val = coins.get(e);
        dp.put(inp, cal(inp - val) + 1L);

        if (val % 25L == 0L) {
            long pre = coins.get(e - 1);
            dp.put(inp, Math.min(dp.get(inp), cal(inp - pre) + 1L));
        }

        return dp.get(inp);
    }
}

// 55 -> 4 (25 10 10 10)

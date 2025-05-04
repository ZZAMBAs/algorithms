package org.zzamba.p32518;

import java.util.*;
import java.util.stream.*;

public class Main {
    static Map<Long, Long> dp = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LongStream.range(0L, 5L).forEach(i -> {
            if (i > 2L)
                dp.put(i, 1L);
            else
                dp.put(i, 0L);
        });

        System.out.println(fdp(sc.nextLong()));
    }

    static long fdp(long num) {
        if (dp.get(num) != null)
            return dp.get(num);

        long realNum = num - 2L;
        long rest = realNum % 3L;

        dp.put(num, 2L);

        if (rest == 0)
            dp.put(num, dp.get(num) + 3L * fdp(realNum / 3L));
        else if (rest == 1)
            dp.put(num, dp.get(num) + fdp(realNum / 3L + 1) + 2L * fdp(realNum / 3L));
        else {
            long v1 = fdp(realNum / 3L + 2) + 2L * fdp(realNum / 3L);
            long v2 = fdp(realNum / 3L + 1) * 2L + fdp(realNum / 3L);

            dp.put(num, dp.get(num) + Math.max(v1, v2));
        }

        return dp.get(num);
    }
}

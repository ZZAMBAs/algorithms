package org.zzamba.p18877;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, M;
    static List<Pair> sortedRange;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        sortedRange = IntStream.range(0, M)
            .mapToObj(i -> new Pair(sc.nextLong(), sc.nextLong()))
            .sorted(Comparator.comparing(Pair::getT))
            .collect(Collectors.toList());
        System.out.print(binarySearch());
    }

    static long binarySearch() {
        long s = 1, e = 1000000000000000000L;
        while (s <= e) {
            long mid = (s + e) / 2L;

            if (canPlace(mid))
                s = mid + 1L;
            else
                e = mid - 1L;
        }

        return e;
    }

    static boolean canPlace(long d) {
        int cnt = 0;
        int curRangeIdx = 0;
        long curD = sortedRange.get(0).t;

        while (cnt < N) {
            Pair curRange = sortedRange.get(curRangeIdx);

            while (curRange.isOver(curD)) {
                curRangeIdx++;

                if (sortedRange.size() == curRangeIdx)
                    return false;

                curRange = sortedRange.get(curRangeIdx);
                curD = Math.max(curD, curRange.t);
            }

            cnt++;
            curD = curD + d;
        }

        return true;
    }

    static class Pair {
        long t;
        long r;

        Pair(long t, long r) {
            this.t = t;
            this.r = r;
        }

        public long getT() {
            return t;
        }

        public boolean isOver(long cmp) {
            return cmp > this.r;
        }
    }
}

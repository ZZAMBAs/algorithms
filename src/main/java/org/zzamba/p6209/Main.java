package org.zzamba.p6209;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int d, n, m, res;
    static List<Integer> islands;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        d = sc.nextInt();
        n = sc.nextInt();
        m = sc.nextInt();
        islands = IntStream.range(0, n).map(i -> sc.nextInt()).sorted().boxed().collect(Collectors.toList());
        islands.add(d);

        res = binarySearch();

        System.out.println(res);

    }

    static int binarySearch() {
        int s = 0;
        int e = d;

        while (s <= e) {
            int mid = (s + e) / 2;
            int deletedCnt = getDeletedCnt(mid);

            if (deletedCnt <= m)
                s = mid + 1;
            else
                e = mid - 1;
        }

        return e;
    }

    static int getDeletedCnt(int minDist) {
        int ret = 0;
        int pre = 0;

        for (int next : islands) {
            int dist = next - pre;

            if (dist < minDist) {
                ret++;
                continue;
            }

            pre = next;
        }

        return ret;
    }
}

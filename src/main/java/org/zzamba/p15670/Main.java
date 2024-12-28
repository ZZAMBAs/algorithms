package org.zzamba.p15670;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N, M, l, r, initAscCnt;
    static int[] heights;
    static List<Integer> ascStartIdx;

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        ascStartIdx = new ArrayList<>();
        N = sc.nextInt();
        M = sc.nextInt();
        heights = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        int curStartIdx = 0;
        int curVal = heights[0];
        for (int i = 1; i < N; i++) {
            if (curVal > heights[i]) {
                ascStartIdx.add(curStartIdx);
                curStartIdx = i;
            }

            curVal = heights[i];
        }
        ascStartIdx.add(curStartIdx);
        initAscCnt = ascStartIdx.size();
        while (M-- > 0) {
            l = sc.nextInt() - 1;
            r = sc.nextInt() - 1;
            int ascStartL = binarySearch(l);
            int ascStartR = binarySearch(r);
            int ascStartLSide = binarySearch(l - 1);
            int ascStartRSide = r + 1 >= N ? initAscCnt : binarySearch(r + 1);

            int res = (ascStartLSide + 1) + (initAscCnt - ascStartRSide) + (r - l + 1) - (ascStartR - ascStartL);

            if (l - 1 >= 0 && heights[r] > heights[l - 1])
                res--;
            if (r + 1 < N && heights[l] < heights[r + 1])
                res--;

            sb.append(res).append('\n');
        }

        System.out.print(sb);
    }

    static int binarySearch(int x) {
        int s = 0, e = ascStartIdx.size() - 1;
        while (s <= e) {
            int mid = (s + e) / 2;
            int midVal = ascStartIdx.get(mid);

            if (midVal > x)
                e = mid - 1;
            else
                s = mid + 1;
        }

        return e;
    }
}

package org.zzamba.p1807;

import java.util.*;

public class Main {
    static final long MAX_LIMIT = 1_000_000_000_000_000L;

    static long K, curIdx;
    static List<Long> endIdx;
    static StringBuilder sb;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sb = new StringBuilder();
        curIdx = 0L;
        endIdx = new ArrayList<>();
        endIdx.add(0L);

        long nowVal = 1;
        long nextVal = 10;
        int curNumSize = 1;
        while (curIdx < MAX_LIMIT) {
            int modIdx = (int)(nowVal % 4L);
            long oneSeqSize = curNumSize * 4L + 3L;

            curIdx += (nextVal - nowVal) / 4L * oneSeqSize;

            long rest = (nextVal - nowVal) % 4L;
            for (int i = 0; i < rest; i++)
                curIdx += (modIdx + i) % 4 == 0 ? curNumSize : curNumSize + 1;

            endIdx.add(curIdx);

            nowVal *= 10L;
            nextVal *= 10L;
            curNumSize++;
        }

        while ((K = sc.nextLong()) > 0)
            sb.append(binarySearch(K - 1)).append('\n');

        System.out.print(sb);
    }

    static char binarySearch(long k) {
        int s = 0;
        int e = endIdx.size() - 1;

        while (s <= e) {
            int mid = (s + e) / 2;
            long midVal = endIdx.get(mid);

            if (midVal > k)
                e = mid - 1;
            else
                s = mid + 1;
        }

        long startNum = 1;
        for (int i = 0; i < e; i++)
            startNum *= 10L;
        long startIdx = endIdx.get(e);
        int x = e + 1;
        long oneSeqSize = 4L * x + 3L;
        int modIdx = (int) (startNum % 4L);

        long q = (k - startIdx) / oneSeqSize;
        startNum += q * 4;
        startIdx += q * oneSeqSize;
        while (startIdx <= k) {
            startIdx += modIdx++ % 4 == 0 ? x : x + 1;
            startNum++;
        }
        startNum--;
        modIdx = (modIdx - 1) % 4;
        startIdx -= modIdx % 4 == 0 ? x : x + 1;


        String str = String.valueOf(startNum);
        if (startNum % 4L == 1L || startNum % 4L == 3L)
            str += "2";
        else if (startNum % 4L == 2L)
            str += "0";

        return str.charAt((int)(k - startIdx));
    }
}

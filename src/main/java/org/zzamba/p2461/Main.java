package org.zzamba.p2461;

import java.util.*;
import java.util.stream.*;

// 투 포인터
public class Main {
    static int N, M;
    static List<Pair> coor = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        IntStream.range(0, N).forEach(i ->
            IntStream.range(0, M).forEach(j -> coor.add(new Pair(sc.nextInt(), i)))
        );
        coor.sort(Comparator.comparing(Pair::getT));

        System.out.print(calMinDiff());
    }

    static int calMinDiff() {
        int ret = Integer.MAX_VALUE;
        int curPairNum = 0;
        int[] count = new int[N];

        int s = 0, e = 0;
        while (s < coor.size() && e < coor.size()) {

            while (e < coor.size()) {
                if (curPairNum == N)
                    break;

                Pair endP = coor.get(e++);
                if (count[endP.r]++ == 0)
                    curPairNum++;
            }


            while (s + 1 < coor.size() && coor.get(s).r == coor.get(s + 1).r && s < e) {
                if (--count[coor.get(s).r] == 0)
                    curPairNum--;
                s++;
            }

            if (curPairNum < N)
                break;

            ret = Math.min(ret, coor.get(e - 1).t - coor.get(s).t);

            if (--count[coor.get(s).r] == 0)
                curPairNum--;
            s++;
        }

        return ret == Integer.MAX_VALUE ? 0 : ret;
    }

    static class Pair {
        int t;
        int r;

        Pair (int t, int r) {
            this.t = t;
            this.r = r;
        }

        int getT() {
            return t;
        }
    }
}

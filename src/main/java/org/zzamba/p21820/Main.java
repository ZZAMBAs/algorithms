package org.zzamba.p21820;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, L, res;
    static int[] papers, papersNum;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        L = sc.nextInt();
        papersNum = new int[100001];
        papers = IntStream.range(0, N).map(i -> {
            int curP = sc.nextInt();
            papersNum[curP]++;
            return curP;
        }).sorted().toArray();
        res = 0;

        int s = 1, e = 100000;
        while (s <= e) {
            int midV = (s + e) / 2;
            int bsVal = binarySearch(midV);

            if (bsVal <= res)
                e = midV - 1;
            else {
                res = midV;
                s = midV + 1;
            }
        }

        System.out.println(res);
    }

    static int binarySearch(int val) {
        int s = 0, e = N - 1;
        while (s <= e) {
            int mid = (s + e) / 2;
            int midV = papers[mid];

            if (midV >= val)
                e = mid - 1;
            else
                s = mid + 1;
        }

        int num = N - (e + 1) + Math.min(papersNum[val - 1], L);

        if (num < val)
            return -1;

        return num;

    }
}

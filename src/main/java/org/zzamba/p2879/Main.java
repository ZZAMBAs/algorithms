package org.zzamba.p2879;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, res;
    static int[] diff;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        res = 0;
        diff = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        IntStream.range(0, N).forEach(i -> diff[i] -= sc.nextInt());

        while (!allClear()) {
            int s = 0;
            while (diff[s] == 0)
                s++;
            int e = s;

            int minVal = Math.abs(diff[s]);
            boolean isInitPositive = diff[s] > 0;

            while (e < N) {
                boolean isEndPositive = diff[e] > 0;

                if (diff[e] == 0 || isInitPositive ^ isEndPositive) {
                    cal(s, e, minVal, isInitPositive);

                    s = e;
                    while (s < N && diff[s] == 0)
                        s++;
                    e = s;

                    if (s >= N)
                        break;

                    minVal = Math.abs(diff[s]);
                    isInitPositive = diff[s] >= 0;
                }
                else
                    minVal = Math.min(minVal, Math.abs(diff[e]));

                e++;
            }

            cal(s, e, minVal, isInitPositive);
        }

        System.out.print(res);
    }

    static boolean allClear() {
        for (int d : diff)
            if (d != 0)
                return false;

        return true;
    }

    static void cal(int s, int e, int minVal, boolean isPositive) {
        if (s == e)
            return;

        for (int i = s; i < e; i++)
            diff[i] += isPositive ? -minVal : minVal;

        res += minVal;
    }
}

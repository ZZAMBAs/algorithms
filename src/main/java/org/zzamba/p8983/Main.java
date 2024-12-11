package org.zzamba.p8983;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N, M, L, res;
    static int[] hunters;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0;
        M = sc.nextInt();
        N = sc.nextInt();
        L = sc.nextInt();
        hunters = IntStream.range(0, M).map(i -> sc.nextInt()).sorted().toArray();

        IntStream.range(0, N).forEach(i -> res += canReach(sc.nextInt(), sc.nextInt()) ? 1 : 0);

        System.out.println(res);
    }

    static boolean canReach(int targetX, int targetY) {
        int s = 0;
        int e = M - 1;

        while (s <= e) {
            int mid = (s + e) / 2;

            if (hunters[mid] >= targetX)
                e = mid - 1;
            else
                s = mid + 1;
        }

        if (e >= 0 && calDistance(hunters[e], targetX, targetY) <= L)
            return true;
        if (e + 1 < M && calDistance(hunters[e + 1], targetX, targetY) <= L)
            return true;

        return false;
    }

    static int calDistance(int hunterX, int targetX, int targetY) {
        return Math.abs(hunterX - targetX) + targetY;
    }
}

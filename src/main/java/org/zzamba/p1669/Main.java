package org.zzamba.p1669;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long X = sc.nextLong();
        long Y = sc.nextLong();
        int res = 0;
        int curLen = 1;

        while (X < Y) {
            X += curLen;

            if (X >= Y)
                res++;
            else
                res += 2;

            Y -= curLen++;
        }

        System.out.println(res);
    }
}

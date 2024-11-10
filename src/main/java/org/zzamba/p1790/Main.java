package org.zzamba.p1790;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int k = sc.nextInt();

        long numRange = 9;
        long numSize = 1;
        long R = -1;
        long Q = -1;

        for (int i = 1; i <= 9; i++) {
            long limit = (numRange + (i == 1 ? 1 : 0)) * numSize;

            if (k < limit) {
                R = k / numSize;
                Q = k % numSize;

                if (numSize == 1)
                    R--;

                break;
            }

            k -= limit;
            numSize++;
            numRange *= 10;
        }

        long val = 1;
        for (int i = 0; i < numSize - 1; i++)
            val *= 10L;
        val += R;

        if (val > N)
            System.out.println(-1);
        else
            System.out.println(String.valueOf(val).charAt((int)Q));
    }
}

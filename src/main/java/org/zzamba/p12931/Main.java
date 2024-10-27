package org.zzamba.p12931;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int res;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        res = 0;

        while (!makeEven(arr)) {
            for (int i = 0; i < N; i++)
                arr[i] /= 2;
            res++;
        }

        System.out.println(res);
    }

    public static boolean makeEven(int[] arr) {
        boolean sw = true;

        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & 1) == 1) {
                arr[i]--;
                res++;
            }

            if (arr[i] != 0)
                sw = false;
        }

        return sw;
    }
}

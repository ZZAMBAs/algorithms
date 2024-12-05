package org.zzamba.p1612;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int comp = 1;
        int res = 1;

        if (N % 2 == 0 || N % 5 == 0)
            System.out.println(-1);
        else {
            while (true) {
                if (comp < N) {
                    comp = comp * 10 + 1;
                    res++;
                }
                else {
                    int val = comp % N;

                    if (val == 0) {
                        System.out.println(res);
                        return;
                    }

                    comp = val * (10 % N) + 1;
                    res++;
                }
            }
        }
    }
}

package org.zzamba.p12888;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        long[] ans = new long[H + 1];
        Arrays.fill(ans, 1);
        for (int i = 0; i < H; i++)
            for (int j = i + 2; j < ans.length; j++)
                ans[j] += ans[i] * 2L;

        System.out.println(ans[H]);
    }
}

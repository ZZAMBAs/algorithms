package org.zzamba.p13422;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int T, N, M, K;
    static int[] money;
    static int moneySum;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        T = sc.nextInt();
        while (T-- > 0) {
            int res = 0;
            N = sc.nextInt();
            M = sc.nextInt();
            K = sc.nextInt();

            money = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
            moneySum = 0;

            for (int i = 0; i < M; i++)
                moneySum += money[i];

            if (moneySum < K)
                res++;

            if (N != M) {
                for (int i = 1; i < N; i++) {
                    moneySum = moneySum - money[i - 1] + money[(i + M - 1) % N];

                    if (moneySum < K)
                        res++;
                }
            }

            sb.append(res).append("\n");
        }

        System.out.print(sb);
    }
}

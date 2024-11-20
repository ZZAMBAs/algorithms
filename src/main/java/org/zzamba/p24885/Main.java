package org.zzamba.p24885;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 그리디 풀이: https://www.acmicpc.net/source/41218932
public class Main {
    static int N, M, K;
    static long res, debt, numOfStock, curMoney; // curMoney: 대출금 포함 현재 돈, debt: 현재 대출금
    static boolean[] buyingSelling; // 0-(N - 1): 매도 여부, N-(2N - 2): 매수 여부
    static List<Integer> values;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        res = M;
        numOfStock = debt = 0L;
        buyingSelling = new boolean[N * 2 - 1];
        values = IntStream.range(0, N).map(i -> sc.nextInt()).boxed().collect(Collectors.toList());

        bruteforce(0);

        System.out.println(res);
    }

    static void bruteforce(int idx) {
        if (idx == 2 * N - 1) {
            curMoney = M;
            debt = 0L;
            numOfStock = 0L;
            cal();
            return;
        }

        buyingSelling[idx] = true;
        bruteforce(idx + 1);
        buyingSelling[idx] = false;
        bruteforce(idx + 1);
    }

    // 매도 후 매수
    // 대출금 남아있다면 매수 불가(아무것도 안함)
    // 마지막 날(N - 1)은 매도만 가능
    // 돌아올 때 반드시 주식 개수는 0개여야 함(전부 매도해야 함)
    static void cal() {
        for (int i = 0; i < N; i++) {
            int sellingIdx = i;
            int buyingIdx = i + N;
            long curValue = values.get(i);

            if (buyingSelling[sellingIdx] && numOfStock > 0L) {
                long profit = numOfStock * curValue;
                numOfStock = 0L;

                curMoney += profit;
            }

            if (i != N - 1 && buyingSelling[buyingIdx] && debt <= curMoney) {
                long temp = curMoney - debt;

                if (temp + temp * K < curValue)
                    continue;

                curMoney -= debt;
                debt = curMoney * K;
                curMoney += debt;

                numOfStock = curMoney / curValue;

                curMoney -= numOfStock * curValue;
            }

        }

        res = Math.max(res, curMoney);
    }
}

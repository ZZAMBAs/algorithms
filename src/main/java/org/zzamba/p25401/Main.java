package org.zzamba.p25401;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static int N;
    static int[] cards;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        cards = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();

        System.out.print(cal());
    }

    static int cal() {
        int minChange = N;

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int diff = (cards[j] - cards[i]) / (j - i);

                int[] start = {i, j};

                for (int sIdx : start) {
                    int leftNum = cards[sIdx] - diff;
                    int rightNum = cards[sIdx] + diff;
                    int changeCnt = 0;

                    for (int left = sIdx - 1; left >= 0; left--) {
                        if (cards[left] != leftNum)
                            changeCnt++;
                        leftNum -= diff;
                    }

                    for (int right = sIdx + 1; right < N; right++) {
                        if (cards[right] != rightNum)
                            changeCnt++;
                        rightNum += diff;
                    }

                    minChange = Math.min(minChange, changeCnt);
                }
            }
        }

        return minChange;
    }
}

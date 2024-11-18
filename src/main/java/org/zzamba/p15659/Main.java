package org.zzamba.p15659;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int N, resMax, resMin;
    static List<Integer> nums, operList;
    static int[] opers; // + - * / 순

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        nums = IntStream.range(0, N).map(i -> sc.nextInt()).boxed().collect(Collectors.toList());
        opers = IntStream.range(0, 4).map(i -> sc.nextInt()).toArray();
        operList = new ArrayList<>();
        resMax = Integer.MIN_VALUE;
        resMin = Integer.MAX_VALUE;

        bruteforce(0);

        System.out.println(resMax);
        System.out.println(resMin);
    }

    static void bruteforce(int idx) {
        if (idx == N - 1) {
            cal();
            return;
        }

        for (int i = 0; i < opers.length; i++) {
            if (opers[i] <= 0)
                continue;

            opers[i]--;
            operList.add(i);
            bruteforce(idx + 1);
            operList.remove(operList.size() - 1);
            opers[i]++;
        }
    }

    static void cal() {
        Deque<Integer> deque = new ArrayDeque<>();
        Deque<Integer> operDeque = new ArrayDeque<>();
        deque.addFirst(nums.get(0));

        for (int i = 0; i < operList.size(); i++) {
            int curOp = operList.get(i);
            if (curOp >= 2) {
                switch (curOp) {
                    case 2 -> deque.addFirst(deque.pollFirst() * nums.get(i + 1));
                    case 3 -> deque.addFirst(deque.pollFirst() / nums.get(i + 1));
                }
            } else {
                deque.addFirst(nums.get(i + 1));
                operDeque.addFirst(curOp);
            }
        }

        while (!operDeque.isEmpty()) {
            int curOp = operDeque.pollLast();
            int a = deque.pollLast();
            int b = deque.pollLast();

            switch (curOp) {
                case 0 -> deque.addLast(a + b);
                case 1 -> deque.addLast(a - b);
            }
        }

        int res = deque.pollLast();

        resMax = Math.max(resMax, res);
        resMin = Math.min(resMin, res);
    }
}

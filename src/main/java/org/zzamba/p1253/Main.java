package org.zzamba.p1253;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int res = 0;
        int N = sc.nextInt();
        Map<Integer, Queue<Integer>> maps = new HashMap<>();
        List<Integer> numbers = IntStream.range(0, N).map(i -> {
            int number = sc.nextInt();

            maps.putIfAbsent(number, new ArrayDeque<>());
            maps.get(number).add(i);

            return number;
        }).boxed().collect(Collectors.toList());

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int iNum = numbers.get(i);
                int jNum = numbers.get(j);
                int sum = iNum + jNum;

                if (maps.containsKey(sum) && !maps.get(sum).isEmpty()) {
                    Queue<Integer> numQ = maps.get(sum);
                    int qSize = numQ.size();

                    for (int k = 0; k < qSize; k++) {
                        int frontIdx = numQ.poll();

                        if (frontIdx == i || frontIdx == j)
                            numQ.add(frontIdx);
                        else {
                            res++;
                        }
                    }
                }

            }
        }

        System.out.println(res);
    }
}

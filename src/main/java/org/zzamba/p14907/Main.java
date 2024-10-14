package org.zzamba.p14907;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static final int MAX_NODE_NUM = 26;

    static int res;
    static List<List<Integer>> inDeg = new ArrayList<>();
    static int[] execTimes = new int[MAX_NODE_NUM]; // 작업을 수행할 수 있는 시간
    static int[] processTimes = new int[MAX_NODE_NUM]; // 작업 수행 시간

    public static void main(String[] args) {
        init();

        for (int i = 0; i < MAX_NODE_NUM; i++) {
            execTimes[i] = calExecTime(i);
            res = Math.max(res, execTimes[i]);
        }

        System.out.print(res);
    }

    private static int calExecTime(int idx) {
        if (execTimes[idx] > 0)
            return execTimes[idx];

        inDeg.get(idx).forEach(adj -> execTimes[idx] = Math.max(execTimes[idx], calExecTime(adj)));
        execTimes[idx] += processTimes[idx];

        return execTimes[idx];
    }

    private static void init() {
        Scanner sc = new Scanner(System.in);
        IntStream.range(0, MAX_NODE_NUM).forEach(i -> inDeg.add(new ArrayList<>()));

        while (sc.hasNext()) {
            String[] input = sc.nextLine().split(" ");

            int idx = input[0].charAt(0) - 65;
            processTimes[idx] = Integer.parseInt(input[1]);

            if (input.length == 3) {
                for (int c : input[2].toCharArray()) {
                    inDeg.get(idx).add(c - 65);
                }
            }
        }
    }
}

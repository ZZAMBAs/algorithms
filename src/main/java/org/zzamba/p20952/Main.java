package org.zzamba.p20952;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static final int MOD = 1_000_000_007;
    static final int MULTI_NUM = 7;

    static int N, M, seq, cnt, ptr;
    static int[] B;
    static boolean[] isRemoved;
    static List<List<int[]>> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inp = br.readLine().split(" ");
        N = Integer.parseInt(inp[0]);
        M = Integer.parseInt(inp[1]);
        cnt = N;
        IntStream.range(0, MULTI_NUM).forEach(i -> list.add(new ArrayList<>()));
        Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::parseInt)
            .forEach(i -> list.get(i % MULTI_NUM).add(new int[]{i, seq++}));

        B = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
        isRemoved = new boolean[B.length];

        IntStream.range(0, M).forEach(Main::plus);
        int sumVal = IntStream.range(0, B.length)
            .filter(i -> !isRemoved[i])
            .reduce(0, (acc, i) -> (acc + B[i]) % MOD);

        System.out.println(cnt);
        System.out.print(
            list.stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(i -> i[1]))
                .map(i -> String.valueOf((i[0] + sumVal) % MOD))
                .collect(Collectors.joining(" "))
        );
    }

    static void plus(int i) {
        int v = B[i];
        int nextPtr = (ptr + MULTI_NUM - (v % MULTI_NUM)) % MULTI_NUM;
        int nextSize = list.get(nextPtr).size();

        if (nextSize == cnt) {
            isRemoved[i] = true;
            return;
        }

        cnt -= nextSize;
        list.set(nextPtr, new ArrayList<>());
        ptr = nextPtr;
    }
}

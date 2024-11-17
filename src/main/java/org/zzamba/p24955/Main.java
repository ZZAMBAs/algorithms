package org.zzamba.p24955;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static final long MOD = 1000000007L;

    static int N, Q;
    static List<Integer> houses;
    static List<List<Integer>> adj;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Q = sc.nextInt();
        houses = IntStream.range(0, N + 1).map(i -> i == 0 ? -1 : sc.nextInt()).boxed().collect(Collectors.toList());
        adj = new ArrayList<>();
        IntStream.range(0, N + 1).forEach(i -> adj.add(new ArrayList<>()));
        IntStream.range(0, N - 1).forEach(i -> {
            int a = sc.nextInt();
            int b = sc.nextInt();

            adj.get(a).add(b);
            adj.get(b).add(a);
        });
        visited = new boolean[N + 1];

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < Q; i++) {
            Arrays.fill(visited, false);
            int s = sc.nextInt();
            int d = sc.nextInt();

            sb.append(cal(s, d, houses.get(s))).append('\n');
        }
        System.out.print(sb);
    }

    static String cal(int cur, int dest, long num) {
        String ret = "ERROR";

        visited[cur] = true;

        if (cur == dest) {
            return String.valueOf(num);
        }

        for (int next : adj.get(cur)) {
            if (!ret.equals("ERROR"))
                return ret;

            if (visited[next])
                continue;

            ret = cal(next, dest, plusNum(num, houses.get(next)));
        }

        return ret;
    }

    static long plusNum(long num, long plus) {
        int plusLen = String.valueOf(plus).length();

        for (int i = 0; i < plusLen; i++)
            num = (num * 10L) % MOD;

        return (num + plus) % MOD;
    }
}

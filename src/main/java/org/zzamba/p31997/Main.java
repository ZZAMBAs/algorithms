package org.zzamba.p31997;

import java.util.*;
import java.util.stream.*;

// 효율적인 풀이는 누적 합 풀이임. https://www.acmicpc.net/source/83323668
public class Main {
    static int N, M, T, res;
    static List<List<Integer>> friends, entering, exiting;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        T = sc.nextInt();
        res = 0;

        visited = new boolean[N + 1];
        friends = new ArrayList<>();
        entering = new ArrayList<>();
        exiting = new ArrayList<>();
        IntStream.rangeClosed(0, N).forEach(i -> friends.add(new ArrayList<>()));
        IntStream.rangeClosed(0, T).forEach(i -> {
            entering.add(new ArrayList<>());
            exiting.add(new ArrayList<>());
        });
        IntStream.rangeClosed(1, N).forEach(i -> {
            entering.get(sc.nextInt()).add(i);
            exiting.get(sc.nextInt()).add(i);
        });
        IntStream.range(0, M).forEach(i -> {
            int c = sc.nextInt();
            int d = sc.nextInt();
            friends.get(c).add(d);
            friends.get(d).add(c);
        });

        StringBuilder sb = new StringBuilder();

        IntStream.range(0, T).forEach(time -> {
            exitAt(time);
            enterAt(time);
            sb.append(res).append("\n");
        });

        System.out.print(sb);
    }

    static void exitAt(int time) {
        exiting.get(time).forEach(idx -> {
            visited[idx] = false;
            for (int friend : friends.get(idx))
                if (visited[friend])
                    res--;
        });
    }

    static void enterAt(int time) {
        entering.get(time).forEach(idx -> {
            visited[idx] = true;
            for (int friend : friends.get(idx))
                if (visited[friend])
                    res++;
        });
    }
}

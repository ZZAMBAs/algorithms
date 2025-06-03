package org.zzamba.p15462;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N;
    static int[] next;
    static boolean[] visited, isCycle;
    static Set<Integer> set;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        visited = new boolean[N];
        isCycle = new boolean[N];
        next = IntStream.range(0, N).map(i -> sc.nextInt() - 1).toArray();

        IntStream.range(0, N).forEach(i -> {
            set = new HashSet<>();
            dfs(i);
            set.forEach(v -> visited[v] = true);
        });

        System.out.print(IntStream.range(0, N).filter(i -> isCycle[i]).count());
    }

    static void dfs(int idx) {
        if (visited[idx])
            return;

        if (isCycle[idx])
            return;

        if (set.contains(idx))
            isCycle[idx] = true;
        else
            set.add(idx);

        dfs(next[idx]);
    }
}

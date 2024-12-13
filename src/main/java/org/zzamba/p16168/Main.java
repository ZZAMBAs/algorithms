package org.zzamba.p16168;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int V, E, evenCnt, oddCnt;
    static List<List<Integer>> adj;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        evenCnt = 0;
        oddCnt = 0;
        V = sc.nextInt();
        E = sc.nextInt();
        adj = new ArrayList<>();
        IntStream.rangeClosed(0, V).forEach(i -> adj.add(new ArrayList<>()));
        IntStream.range(0, E).forEach(i -> {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();

            adj.get(v1).add(v2);
            adj.get(v2).add(v1);
        });

        for (int i = 1; i <= V; i++) {
            int edgeNum = adj.get(i).size();
            oddCnt += edgeNum % 2;
            evenCnt += (edgeNum + 1) % 2;
        }

        if (allConnected() && evenCnt == V || (oddCnt == 2 && evenCnt == V - 2))
            System.out.println("YES");
        else
            System.out.println("NO");
    }

    static boolean allConnected() {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(1);
        boolean[] isVisited = new boolean[V + 1];
        isVisited[1] = true;
        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : adj.get(cur)) {
                if (!isVisited[next]) {
                    isVisited[next] = true;
                    q.add(next);
                }
            }

        }

        for (int i = 1; i <= V; i++)
            if (!isVisited[i])
                return false;
        return true;
    }
}

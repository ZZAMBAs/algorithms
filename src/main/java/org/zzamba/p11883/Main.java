package org.zzamba.p11883;

import java.util.*;
import java.util.stream.*;

public class Main {
    static PairInt[] visited;
    static int[] dl = new int[]{3, 5, 8};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        visited = new PairInt[1000001];

        Arrays.stream(dl).forEach(Main::bfs);

        String res = IntStream.range(0, T)
            .mapToObj(i -> getNum(sc.nextInt()))
            .collect(Collectors.joining("\n"));
        System.out.print(res);
    }

    static void bfs(int s) {
        boolean[] tempVisited = new boolean[1000001];
        Queue<PairInt> q = new ArrayDeque<>(); // {이전 인덱스, 현재 인덱스}
        int level = 1; // 자릿수
        q.add(new PairInt(0, s));
        while (!q.isEmpty()) {
            int qSize = q.size();
            while (qSize-- > 0) {
                PairInt cur = q.poll();
                PairInt curV = visited[cur.r];
                int preIdx = cur.t;

                if (curV != null && curV.r <= level)
                    continue;

                visited[cur.r] = new PairInt(preIdx, level);

                Arrays.stream(dl).forEach(d -> {
                    int next = cur.r + d;

                    if (next <= 1000000 && !tempVisited[next]) {
                        q.add(new PairInt(cur.r, next));
                        tempVisited[next] = true;
                    }
                });
            }

            level++;
        }
    }

    static String getNum(int n) {
        if (visited[n] == null)
            return "-1";

        StringBuilder sb = new StringBuilder();
        Deque<Integer> stk = new ArrayDeque<>();

        while (n > 0) {
            stk.push(n - visited[n].t);
            n = visited[n].t;
        }

        while (!stk.isEmpty())
            sb.append(stk.pop());

        return sb.toString();
    }

    static class PairInt {
        int t; // 이전 인덱스
        int r; // 자릿수 (1이면 시작점)

        PairInt(int t, int r) {
            this.t = t;
            this.r = r;
        }
    }
}

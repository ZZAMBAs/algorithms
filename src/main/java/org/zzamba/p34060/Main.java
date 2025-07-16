package org.zzamba.p34060;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, res;
    static int[] received;
    static int[][] dl = new int[][]{ {1, 0}, {0, 1}, {-1, 0}, {0, -1} };
    static Map<Integer, List<Integer>> coor = new HashMap<>(); // {x좌표, {...y좌표} }
    static Map<Integer, Map<Integer, Boolean>> visited = new HashMap<>(); // {x좌표, {y좌표, 방문 여부} }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        received = IntStream.range(0, N)
            .map(i -> {
                try {
                    return Integer.parseInt(br.readLine());
                } catch (Exception e) { return -1; }
            })
            .toArray();

        int curX = 0;
        int pre = Integer.MAX_VALUE;
        for (int v : received) {
            if (pre >= v)
                curX++;

            coor.putIfAbsent(curX, new ArrayList<>());
            List<Integer> list = coor.get(curX);
            list.add(v);
            pre = v;
        }

        coor.forEach((key, value) -> {
			visited.putIfAbsent(key, new HashMap<>());
			Map<Integer, Boolean> map = visited.get(key);
			value.forEach(v -> map.put(v, false));
		});

        coor.forEach((key, value) -> value.forEach(v -> bfs(key, v)));

        System.out.println(res);
        System.out.println(N); // 최대
    }

    static void bfs(int x, int y) {
        if (visited.get(x).get(y))
            return;

        res++;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{x, y});
        visited.get(x).put(y, true);
        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int[] d : dl) {
                int nextX = cur[0] + d[0];
                int nextY = cur[1] + d[1];

                if (isOk(nextX, nextY)) {
                    visited.get(nextX).put(nextY, true);
                    q.add(new int[]{nextX, nextY});
                }
            }
        }

    }

    static boolean isOk(int x, int y) {
        if (visited.get(x) == null || visited.get(x).get(y) == null)
            return false;

        return !visited.get(x).get(y);
    }

}

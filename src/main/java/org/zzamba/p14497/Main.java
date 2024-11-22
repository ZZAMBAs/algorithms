package org.zzamba.p14497;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static final int[][] dl = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

    static int N, M, sRow, sCol, dRow, dCol;
    static List<String> map;
    static int[][] dist;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        dist = new int[N][M];
        sRow = sc.nextInt() - 1;
        sCol = sc.nextInt() - 1;
        dRow = sc.nextInt() - 1;
        dCol = sc.nextInt() - 1;
        map = IntStream.range(0, N).mapToObj(i -> sc.next()).collect(Collectors.toList());
        IntStream.range(0, N).forEach(i -> Arrays.fill(dist[i], Integer.MAX_VALUE));

        System.out.println(dijkstra() + 1);
    }

    static int dijkstra() {
        PriorityQueue<Pair<Pair<Integer, Integer>, Integer>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getR)); // { {row, col}, weight }
        pq.add(new Pair<>(new Pair<>(sRow, sCol), 0));
        while (!pq.isEmpty()) {
            Pair<Pair<Integer, Integer>, Integer> p = pq.poll();
            int curRow = p.t.t;
            int curCol = p.t.r;
            int curW = p.r;

            if (dist[curRow][curCol] < curW)
                continue;

            if (curRow == dRow && curCol == dCol)
                return curW;

            for (int[] d : dl) {
                int nextR = curRow + d[0];
                int nextC = curCol + d[1];

                if (!inRange(nextR, nextC))
                    continue;

                int nextW = curW + (map.get(nextR).charAt(nextC) == '1' ? 1 : 0);

                if (dist[nextR][nextC] <= nextW)
                    continue;

                dist[nextR][nextC] = nextW;
                pq.add(new Pair<>(new Pair<>(nextR, nextC), nextW));
            }
        }

        return -1;
    }

    static boolean inRange(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        public R getR() {
            return r;
        }
    }
}

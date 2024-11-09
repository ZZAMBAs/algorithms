package org.zzamba.p4485;

import java.util.*;

public class Main {
    static int N;
    static int[][] dl = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = 1;
        N = sc.nextInt();
        List<Integer> res = new ArrayList<>();

        while (N > 0) {
            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = sc.nextInt();
                }
            }

            res.add(cal(map));

            N = sc.nextInt();
        }

        StringBuilder sb = new StringBuilder();
        for (int r : res)
            sb.append("Problem ").append(T++).append(": ").append(r).append("\n");

        System.out.print(sb);
    }

    static int cal(int[][] map) {
        int ret = map[0][0];
        int[][] minRupees = new int[N][N];
        for (int[] row : minRupees)
            Arrays.fill(row, Integer.MAX_VALUE);
        minRupees[0][0] = map[0][0];
        PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> pq = new PriorityQueue<>(Comparator.comparing(Pair::getT)); // { 최소 루피, {행, 열} }
        pq.add(new Pair<>(ret, new Pair<>(0, 0)));

        while (!pq.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> curP = pq.poll();
            int rupee = curP.t;
            int row = curP.r.t;
            int col = curP.r.r;

            if (minRupees[row][col] < rupee)
                continue;

            if (row == N - 1 && col == N - 1)
                return rupee;

            for (int[] d : dl) {
                int nextR = row + d[0];
                int nextC = col + d[1];

                if (inRange(nextR, nextC)) {
                    int nextRupee = rupee + map[nextR][nextC];

                    if (minRupees[nextR][nextC] <= nextRupee)
                        continue;

                    minRupees[nextR][nextC] = nextRupee;
                    pq.add(new Pair<>(nextRupee, new Pair<>(nextR, nextC)));
                }
            }
        }

        return -1;
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        public T getT() {
            return t;
        }
    }
}

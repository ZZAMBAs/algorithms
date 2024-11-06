package org.zzamba.p17142;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Main {
    static final int EMPTY = 0;
    static final int WALL = 1;
    static final int VIRUS = 2;

    static int N, M, emptyNum, res;
    static int[][] map;
    static List<Pair> viruses;
    static boolean[] selected;
    static int[][] dl = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = input[0];
        M = input[1];
        map = new int[N][N];
        res = Integer.MAX_VALUE;
        viruses = new ArrayList<>();
        emptyNum = 0;

        for (int i = 0; i < N; i++) {
            int[] rowInput = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j < N; j++) {
                int val = rowInput[j];
                map[i][j] = val;

                if (val == VIRUS)
                    viruses.add(new Pair(i, j));
                if (val == EMPTY)
                    emptyNum++;
            }
        }

        selected = new boolean[viruses.size()];
        selectAndVisit(0, 0);

        System.out.print(res == Integer.MAX_VALUE ? -1 : res);
    }

    static void selectAndVisit(int idx, int selectedCnt) {
        if (idx == viruses.size()) {
            if (selectedCnt == M)
                res = Math.min(res, cal());
            return;
        }

        if (M - selectedCnt - (viruses.size() - idx + 1) > 0)
            return;

        selected[idx] = true;
        selectAndVisit(idx + 1, selectedCnt + 1);
        selected[idx] = false;
        selectAndVisit(idx + 1, selectedCnt);
    }

    static int cal() {
        int ret = 0;
        int emptyCnt = 0;
        Queue<Pair> virusesQ = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                Pair virus = viruses.get(i);
                virusesQ.add(new Pair(virus.x, virus.y));
                visited[virus.x][virus.y] = true;
            }
        }

        while (!virusesQ.isEmpty()) {
            if (emptyCnt == emptyNum)
                break;

            ret++;

            int qSize = virusesQ.size();
            for (int i = 0; i < qSize; i++) {
                Pair curVirus = virusesQ.poll();

                for (int[] d : dl) {
                    int nextR = curVirus.x + d[0];
                    int nextC = curVirus.y + d[1];

                    if (inRange(nextR, nextC) && map[nextR][nextC] != WALL && !visited[nextR][nextC]) {
                        virusesQ.add(new Pair(nextR, nextC));
                        visited[nextR][nextC] = true;

                        if (map[nextR][nextC] == EMPTY)
                            emptyCnt++;
                    }
                }
            }
        }

        return emptyCnt == emptyNum ? ret : Integer.MAX_VALUE;
    }

    static boolean inRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

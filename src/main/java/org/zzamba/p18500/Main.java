package org.zzamba.p18500;

import java.util.*;
import java.io.*;

// 1. 양쪽에서 던지는 막대를 통해 미네랄을 일단 없앤다.
// 2. 없앤 부분 기준으로 그래프 탐색을 진행해 공중에 떠있는 클러스터를 찾고 밑으로 내린다.
// 개선점:
// - 부서진 미네랄 4방향만 BFS 체크하면 되는데 앞뒤 행, 열을 전부 체크하고 있음
// - 밑으로 클러스터를 내릴 때 R*C 배열을 새로 만들고 하나씩 미는 방식을 선택함.
//      이것보다는 그냥 클러스터 좌표만 저장한 뒤, 각 좌표에서 내릴 수 있는 길이를 계산하고 그 중 최소만큼만 내리면 된다. 현재는 메모리/시간 낭비 큼
// - 두 개 이상의 클러스터가 떨어지는 경우는 없는데 리스트로 받고 이를 처리함
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static boolean[][] cave;
    static int R, C, N;
    static int[] throwRows;
    static int[][] dl = new int[][]{ {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    public static void main(String[] args) throws Exception {
        int[] nm = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        R = nm[0];
        C = nm[1];
        cave = new boolean[R][C];
        for (int i = R - 1; i >= 0; i--) {
            String row = br.readLine();
            cave[i] = new boolean[C];

            int idx = 0;
            for (char c : row.toCharArray()) {
                if (c == 'x')
                    cave[i][idx] = true;
                idx++;
            }
        }
        N = Integer.parseInt(br.readLine());
        throwRows = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        for (int turn = 0; turn < throwRows.length; turn++) {
            switch (turn % 2) {
                case 0 -> throwLeft(throwRows[turn] - 1);
                case 1 -> throwRight(throwRows[turn] - 1);
            }
            move(throwRows[turn] - 1);
        }

        printRes();
    }

    static void throwLeft(int row) {
        int c = 0;

        while (c < C && !cave[row][c]) {
            c++;
        }

        if (c >= C)
            return;

        cave[row][c] = false;
    }

    static void throwRight(int row) {
        int c = C - 1;

        while (c >= 0 && !cave[row][c]) {
            c--;
        }

        if (c < 0)
            return;

        cave[row][c] = false;
    }

    static void move(int row) {
        List<boolean[][]> downList = new ArrayList<>();
        boolean[][] isVisited = new boolean[R][C];

        for (int r = Math.max(0, row - 1); r < Math.min(row + 2, R); r++)
            for (int c = 0; c < C; c++)
                if (cave[r][c] && !isVisited[r][c])
                    downList.add(getDownList(r, c, isVisited));

        downList.forEach(Main::down);
    }

    static boolean[][] getDownList(int row, int c, boolean[][] isVisited) {
        boolean isFixed = false;
        boolean[][] ret = new boolean[R][C];

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{row, c});
        isVisited[row][c] = true;
        ret[row][c] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int curR = cur[0];
            int curC = cur[1];

            if (curR == 0)
                isFixed = true;

            for (int[] d : dl) {
                int nextR = curR + d[0];
                int nextC = curC + d[1];

                if (inRange(nextR, nextC) && !isVisited[nextR][nextC] && cave[nextR][nextC]) {
                    int[] next = new int[]{nextR, nextC};
                    q.add(next);
                    ret[nextR][nextC] = true;
                    isVisited[nextR][nextC] = true;
                }
            }
        }

        if (!isFixed)
            return ret;
        return null;
    }

    static void down(boolean[][] component) {
        if (component == null)
            return;

        // print(component);

        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++)
                if (cave[r][c] == component[r][c])
                    cave[r][c] = false;

        int downIdx = 0;
        int maxDownIdx = getMaxDownIdx(component);
        boolean isOk = true;
        do {
            downIdx++;

            if(downIdx > maxDownIdx || !checkComponent(downIdx, component))
                isOk = false;
        } while (isOk);
        downIdx--;

        for (int r = 0; r < R - downIdx; r++)
            for (int c = 0; c < C; c++)
                cave[r][c] = (cave[r][c] || component[r + downIdx][c]);
    }

    static boolean checkComponent(int downIdx, boolean[][] component) {
        for (int r = 0; r < R - downIdx; r++) {
            for (int c = 0; c < C; c++) {
                if (component[r + downIdx][c] && cave[r][c])
                    return false;
            }
        }

        return true;
    }

    static int getMaxDownIdx(boolean[][] component) {
        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++)
                if (component[r][c])
                    return r;
        return -1;
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    static void printRes() {
        StringBuilder sb = new StringBuilder();

        for (int i = R - 1; i >= 0; i--) {
            for (int j = 0; j < C; j++)
                sb.append(cave[i][j] ? "x" : ".");
            sb.append("\n");
        }

        System.out.print(sb.toString());
    }

    static void print(boolean[][] component) {
        System.out.println("---component---");
        for (int i = R - 1; i >= 0; i--) {
            for (int j = 0; j < C; j++)
                System.out.print(component[i][j] ? "x" : ".");
            System.out.println();
        }
        System.out.println("---");
    }
}

package org.zzamba.p17090;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, M;
    static boolean[][] isVisited, canExit;
    static String[] map;
    static Map<Character, int[]> dir = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = IntStream.range(0, N).mapToObj(i -> sc.next()).toArray(String[]::new);
        isVisited = new boolean[N][M];
        canExit = new boolean[N][M];
        dir.put('D', new int[]{1, 0});
        dir.put('U', new int[]{-1, 0});
        dir.put('L', new int[]{0, -1});
        dir.put('R', new int[]{0, 1});

        IntStream.range(0, N).forEach(i -> IntStream.range(0, M).forEach(j -> {
            if (!isVisited[i][j])
                dfs(i, j);
        }));

        System.out.print(IntStream.range(0, N).map(i ->
                (int)IntStream.range(0, M).filter(j -> canExit[i][j]).count())
            .sum());
    }

    static boolean dfs(int r, int c) {
        if (outRange(r, c) || canExit[r][c])
            return true;
        if (isVisited[r][c])
            return false;

        isVisited[r][c] = true;

        int nextR = r + dir.get(map[r].charAt(c))[0];
        int nextC = c + dir.get(map[r].charAt(c))[1];

        canExit[r][c] = dfs(nextR, nextC);
        return canExit[r][c];
    }

    static boolean outRange(int r, int c) {
        return !(r >= 0 && r < N && c >= 0 && c < M);
    }
}

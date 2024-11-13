package org.zzamba.p5549;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    static final char JUNGLE = 'J';
    static final char OCEAN = 'O';
    static final char ICE = 'I';

    static int N, M, K;
    static String[] map;
    static int[][] jungle, ocean, ice;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        map = IntStream.range(0, N).mapToObj(i -> sc.next()).toArray(String[]::new);
        jungle = new int[N + 1][M + 1];
        ocean = new int[N + 1][M + 1];
        ice = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                char curRegion = map[i - 1].charAt(j - 1);

                fillPrefix(jungle, i, j, JUNGLE, curRegion);
                fillPrefix(ocean, i, j, OCEAN, curRegion);
                fillPrefix(ice, i, j, ICE, curRegion);
            }
        }

        StringBuilder sb = new StringBuilder();
        IntStream.range(0, K).forEach(i -> {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt(), d = sc.nextInt();

            sb.append(calPrefix(jungle, a, b, c, d)).append(' ');
            sb.append(calPrefix(ocean, a, b, c, d)).append(' ');
            sb.append(calPrefix(ice, a, b, c, d)).append('\n');
        });
        System.out.print(sb);
    }

    static void fillPrefix(int[][] prefix, int i, int j, char match, char comp) {
        prefix[i][j] = prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1] + (match == comp ? 1 : 0);
    }

    static int calPrefix(int[][] prefix, int startI, int startJ, int endI, int endJ) {
        return prefix[endI][endJ] - prefix[startI - 1][endJ] - prefix[endI][startJ - 1] + prefix[startI - 1][startJ - 1];
    }
}

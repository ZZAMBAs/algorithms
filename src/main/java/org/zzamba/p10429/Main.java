package org.zzamba.p10429;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static final int BOARD_LEN = 3;

    static int N, M;
    static String[] board = new String[BOARD_LEN];
    static boolean[][] visited = new boolean[BOARD_LEN][BOARD_LEN];
    static int[][] dl = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
    static boolean sw;

    public static void main(String[] args) {
        init();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 && !sw; j++) {
                if ((i + j) % 2 == 0) {
                    List<Pair> path = new ArrayList<>();
                    bruteforce(i, j, path);
                }
            }
        }

        if (!sw)
            System.out.println(0);
    }

    static void bruteforce(int i, int j, List<Pair> path) {
        visited[i][j] = true;
        path.add(new Pair(i, j));

        if (path.size() == 2 * M - 1) {
            if (N == calculate(path)) {
                sw = true;
                System.out.println(1);
                System.out.print(path.stream().map(p -> p.x + " " + p.y).collect(Collectors.joining("\n")));
            }

            path.remove(path.size() - 1);
            visited[i][j] = false;
            return;
        }

        for (int[] d : dl) {

            if (sw)
                return;

            int nextR = i + d[0];
            int nextC = j + d[1];

            if (inRange(nextR, nextC) && !visited[nextR][nextC]) {
                bruteforce(nextR, nextC, path);
            }
        }

        path.remove(path.size() - 1);
        visited[i][j] = false;
    }

    static int calculate(List<Pair> path) {
        int ret = board[path.get(0).x].charAt(path.get(0).y) - 48;

        for (int i = 1; i < path.size(); i += 2) {
            char operator = board[path.get(i).x].charAt(path.get(i).y);
            int operand = board[path.get(i + 1).x].charAt(path.get(i + 1).y) - 48;

            ret += switch (operator) {
                case '+' -> operand;
                case '-' -> -operand;
				default -> 0;
			};
        }

        return ret;
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < BOARD_LEN && c >= 0 && c < BOARD_LEN;
    }

    static void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        for (int i = 0; i < BOARD_LEN; i++)
            board[i] = sc.next();
        for (int i = 0; i < BOARD_LEN; i++) {
            for (int j = 0; j < BOARD_LEN; j++) {
                visited[i][j] = false;
            }
        }
        sw = false;
    }

    static class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

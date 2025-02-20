package org.zzamba.p2044;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        Screen monitor = new Screen(sc.nextInt(), sc.nextInt());
        String[] inputs = IntStream.range(0, monitor.row).mapToObj(i -> sc.next()).toArray(String[]::new);
        boolean[][] scanned = new boolean[monitor.row][monitor.col];

        for (int i = 0; i < monitor.row; i++)
            for (int j = 0; j < monitor.col; j++)
                if (inputs[i].charAt(j) == '+' && !scanned[i][j])
                    monitor.windows.add(scan(i, j, inputs, scanned));

        monitor.printCascade();
    }

    static Window scan(int startR, int startC, String[] inputs, boolean[][] scanned) {
        int r = 1;
        int c = 1;
        StringBuilder titleBuilder = new StringBuilder();

        while (true) {
            char curChar = inputs[startR].charAt(startC + c++);

            if (curChar >= 'a' && curChar <= 'z')
                titleBuilder.append(curChar);

            if (curChar == '+')
                break;
        }

        while (true) {
            if (inputs[startR + r++].charAt(startC) == '+')
                break;
        }

        for (int row = startR; row < startR + r; row++)
            scanned[row][startC] = scanned[row][startC + c - 1] = true;
        for (int col = startC; col < startC + c; col++)
            scanned[startR][col] = scanned[startR + r - 1][col] = true;

        return new Window(r, c, titleBuilder.toString());
    }

    static class Screen {
        int row;
        int col;
        char[][] screen;
        List<Window> windows;

        public Screen(int row, int col) {
            this.row = row;
            this.col = col;
            this.screen = new char[row][col];
            this.windows = new ArrayList<>();
            IntStream.range(0, row).forEach(i -> Arrays.fill(screen[i], '.'));
        }

        public void printCascade() {
            windows.sort(Comparator.comparing(Window::getTitle));
            boolean[][] written = new boolean[row][col];

            for (int idx = windows.size() - 1; idx >= 0; idx--) {
                Window curWindow = windows.get(idx);

                write(idx, idx, '+', written);
                write(idx + curWindow.row - 1, idx, '+', written);
                write(idx, idx + curWindow.col - 1, '+', written);
                write(idx + curWindow.row - 1, idx + curWindow.col - 1, '+', written);

                for (int colW = 1; colW < curWindow.col - 1; colW++) {
                    write(idx, idx + colW, '-', written);
                    write(idx + curWindow.row - 1, idx + colW, '-', written);
                }
                for (int rowW = 1; rowW < curWindow.row - 1; rowW++) {
                    write(idx + rowW, idx + curWindow.col - 1, '|', written);
                    write(idx + rowW, idx, '|', written);
                }
                for (int i = 0; i < curWindow.row; i++)
                    for (int j = 0; j < curWindow.col; j++)
                        written[idx + i][idx + j] = true;

                int titleStart = (curWindow.col - curWindow.title.length()) / 2;
                screen[idx][idx + titleStart - 1] = screen[idx][idx + titleStart + curWindow.title.length()] = '|';
                for (int titleIdx = 0; titleIdx < curWindow.title.length(); titleIdx++)
                    screen[idx][idx + titleStart + titleIdx] = curWindow.title.charAt(titleIdx);

            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++)
                    System.out.print(screen[i][j]);
                System.out.println();
            }
        }

        private void write(int r, int c, char ch, boolean[][] written) {
            if (written[r][c])
                return;

            written[r][c] = true;
            screen[r][c] = ch;
        }
    }

    static class Window {
        int row;
        int col;
        String title;

        public Window(int row, int col, String title) {
            this.row = row;
            this.col = col;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}

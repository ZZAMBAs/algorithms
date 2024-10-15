package org.zzamba.p30023;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final int COLOR_NUM = 3;

    static int N;
    static int[] lights;

    public static void main(String[] args) {
        int res = Integer.MAX_VALUE;

        init();

        for (int color = 0; color < COLOR_NUM; color++) { // 통일 컬러
            res = Math.min(res, calMinColorSwitch(color));
        }

        System.out.print(res == Integer.MAX_VALUE ? -1 : res);
    }

    static int calMinColorSwitch(int color) {
        int ret = 0;
        int[] tempLights = Arrays.copyOf(lights, lights.length);

        for (int i = 0; i <= N - 3; i++) {
            while (tempLights[i] != color) {
                tempLights[i] = (tempLights[i] + 1) % COLOR_NUM;
                tempLights[i + 1] = (tempLights[i + 1] + 1) % COLOR_NUM;
                tempLights[i + 2] = (tempLights[i + 2] + 1) % COLOR_NUM;

                ret++;
            }
        }

        if (tempLights[N - 1] == color && tempLights[N - 2] == color)
            return ret;
        return Integer.MAX_VALUE;
    }

    static void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        String input = sc.next();
        lights = new int[N];
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case 'R': lights[i] = 0; break;
                case 'G': lights[i] = 1; break;
                case 'B': lights[i] = 2; break;
			}
		}
    }
}

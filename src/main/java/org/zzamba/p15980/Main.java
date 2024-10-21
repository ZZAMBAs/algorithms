package org.zzamba.p15980;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int res = Integer.MAX_VALUE;
        int resBird = 0;
        int[] disturbances = new int[M];
        int[] birdW = new int[N];
        String[] birdSound = new String[N];

        for (int i = 0; i < N; i++) {
            birdW[i] = sc.next().charAt(0) == 'L' ? -1 : 1;

            birdSound[i] = sc.next();
            for (int j = 0; j < M; j++) {
                disturbances[j] += (birdSound[i].charAt(j) - 48) * birdW[i];
            }
        }
        for (int i = 1; i < M; i++) {
            disturbances[i] += disturbances[i - 1];
        }

        for (int i = 0; i < N; i++) {
            int w = -birdW[i];
            int acc = 0;
            int curDis = 0;
            for (int j = 0; j < M; j++) {
                acc += birdSound[i].charAt(j) - 48;

                curDis = Math.max(curDis, Math.abs(disturbances[j] + acc * w));
            }

            if (res > curDis) {
                res = curDis;
                resBird = i + 1;
            }
        }

        System.out.println(resBird);
        System.out.println(res);
    }
}

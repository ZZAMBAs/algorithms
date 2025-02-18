package org.zzamba.p1512;

import java.util.Scanner;

public class Main {
    static int M, res;
    static String str;

    public static void main(String[] args) {
        Scanner sc = new  Scanner(System.in);
        M = sc.nextInt();
        str = sc.next();
        res = Integer.MAX_VALUE;

        for (int P = M; P > 0; P--) {
            int cnt = 0;

            for (int fIdx = 0; fIdx < P; fIdx++) {
                int[] charCnt = new int[4];
                int idx = fIdx;
                while (idx < str.length()) {
                    switch (str.charAt(idx)) {
                        case 'A' -> charCnt[0]++;
                        case 'C' -> charCnt[1]++;
                        case 'G' -> charCnt[2]++;
                        case 'T' -> charCnt[3]++;
                    }

                    idx += P;
                }

                cnt += Math.min(
                    Math.min(charCnt[0] + charCnt[1] + charCnt[2], charCnt[0] + charCnt[1] + charCnt[3]),
                    Math.min(charCnt[1] + charCnt[2] + charCnt[3], charCnt[0] + charCnt[2] + charCnt[3])
                );
            }
            res = Math.min(res, cnt);
        }

        System.out.println(res);
    }
}

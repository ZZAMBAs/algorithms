package org.zzamba.p2195;

import java.util.Scanner;

public class Main {
    static String S, P;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        S = sc.next();
        P = sc.next();

        int PIdx = 0;
        int res = 0;

        while (PIdx < P.length()) {
            int maxSub = 0;
            int curSub = 0;

            for (int SIdx = 0; SIdx < S.length() && PIdx + curSub < P.length(); SIdx++) {
                if (S.charAt(SIdx) != P.charAt(PIdx + curSub))
                    curSub = 0;

                if (S.charAt(SIdx) == P.charAt(PIdx + curSub)) {
                    curSub++;
                    maxSub = Math.max(maxSub, curSub);
                }
            }

            res++;
            PIdx += maxSub;
        }

        System.out.print(res);
    }
}

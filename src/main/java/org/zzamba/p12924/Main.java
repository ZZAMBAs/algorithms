package org.zzamba.p12924;

import java.util.*;

public class Main {
    static int A, B;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        A = sc.nextInt();
        B = sc.nextInt();

        int res = 0;
        for (int i = A; i <= B; i++)
            res += countAwesomeNum(i);

        System.out.print(res);
    }

    static int countAwesomeNum(int num) {
        int ret = 0;
        String numStr = String.valueOf(num);
        Set<Integer> dup = new HashSet<>();

        int numLen = numStr.length();
        for (int i = 1; i < numLen; i++) {
            int line = numLen - i;

            int reverseNum = Integer.parseInt(numStr.substring(line, numLen) + numStr.substring(0, line));

            if (reverseNum <= num || reverseNum > B || dup.contains(reverseNum))
                continue;

            dup.add(reverseNum);
            ret++;
        }

        return ret;
    }
}

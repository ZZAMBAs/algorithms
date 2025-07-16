package org.zzamba.p16472;

import java.util.*;

public class Main {
    static int N;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        String str = sc.next();

        System.out.print(getMaxLength(str));
    }

    static int getMaxLength(String str) {
        int ret = 0;
        int curLen = 0;
        int curUsed = 0;
        Map<Character, Integer> used = new HashMap<>(); // {문자, 개수}

        int s = 0, e = 0;

        while (e < str.length()) {
            char endChar = str.charAt(e);

            used.put(endChar, used.getOrDefault(endChar, 0) + 1);

            if (used.get(endChar) == 1)
                curUsed++;

            curLen++;

            while (curUsed > N) {
                char startChar = str.charAt(s);
                curLen--;
                used.put(startChar, used.get(startChar) - 1);

                if (used.get(startChar) == 0)
                    curUsed--;

                s++;
            }

            ret = Math.max(ret, curLen);
            e++;
        }

        return ret;
    }
}

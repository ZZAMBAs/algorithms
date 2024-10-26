package org.zzamba.p1599;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jetbrains.annotations.NotNull;

public class Main {
    static String[] minsikChar;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        minsikChar = "a b k d e g h i l m n ng o p r s t u w y".split(" ");

        int N = sc.nextInt();
        System.out.print(IntStream.range(0, N)
            .mapToObj(i -> new Minsik(sc.next()))
            .sorted()
            .map(Minsik::getVal)
            .collect(Collectors.joining("\n"))
        );


    }

    static class Minsik implements Comparable<Minsik> {
        String val;

        public Minsik(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        @Override
        public int compareTo(@NotNull Minsik minsik) {
            for (int i = 0; i < Math.min(this.val.length(), minsik.val.length()); ) {
                String s1 = getChar(i);
                String s2 = minsik.getChar(i);

                int s1MinsikCharIdx = findMinsikCharIdx(s1);
                int s2MinsikCharIdx = findMinsikCharIdx(s2);

                if (s1MinsikCharIdx > s2MinsikCharIdx)
                    return 1;
                else if (s1MinsikCharIdx < s2MinsikCharIdx)
                    return -1;

                i += Math.max(s1.length(), s2.length());
            }

            if (this.val.length() < minsik.val.length())
                return -1;
            else if (this.val.length() > minsik.val.length())
                return 1;

            return 0;
        }

        private String getChar(int idx) {
            String ret = "" + this.val.charAt(idx);
            if (ret.equals("n") && val.length() > idx + 1 && val.charAt(idx + 1) == 'g')
                return "ng";
            return ret;
        }

        private int findMinsikCharIdx(String s) {
            for (int i = 0; i < minsikChar.length; i++)
                if (minsikChar[i].equals(s))
                    return i;
            return -1;
        }
    }
}

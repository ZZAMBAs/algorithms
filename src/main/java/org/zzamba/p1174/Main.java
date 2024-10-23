package org.zzamba.p1174;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<String> nums;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        nums = new ArrayList<>();

        for (int i = 48; i < 58; i++)
            makeNum("" + (char)i);
        nums.sort(Comparator.comparingLong(Long::parseLong));

        int n = sc.nextInt();

        if (n > nums.size())
            System.out.println(-1);
        else
            System.out.println(nums.get(n - 1));
    }

    static void makeNum(String num) {
        nums.add(num);

        char curC = num.charAt(0);

        for (int next = curC + 1; next <= '9'; next++) {
            char nextC = (char)next;
            makeNum(nextC + num);
        }
    }
}

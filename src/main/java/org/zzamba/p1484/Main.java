package org.zzamba.p1484;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> res = new ArrayList<>();
        int G = sc.nextInt();

        for (int i = 1; i * i <= G; i++) {
            if (G % i != 0)
                continue;

            int a = Math.max(i, G / i);
            int b = Math.min(i, G / i);

            if ((a + b) % 2 != 0)
                continue;

            int curW = (a + b) / 2;
            int preW = a - curW;

            if (preW <= 0)
                continue;

            res.add(curW);
        }

        if (!res.isEmpty())
            System.out.print(res.stream().sorted().map(String::valueOf).collect(Collectors.joining("\n")));
        else
            System.out.println(-1);
    }
}

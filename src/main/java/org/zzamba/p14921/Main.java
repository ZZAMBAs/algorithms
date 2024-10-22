package org.zzamba.p14921;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<Integer> liquids = IntStream.range(0, N).mapToObj(i -> sc.nextInt()).sorted().collect(Collectors.toList());

        int res = Integer.MAX_VALUE;

        int s = 0, e = liquids.size() - 1;

        while (s < e) {
            int sum = liquids.get(s) + liquids.get(e);

            if (Math.abs(sum) < Math.abs(res))
                res = sum;

            if (sum >= 0)
                e--;
            else
                s++;
        }

        System.out.println(res);
    }
}

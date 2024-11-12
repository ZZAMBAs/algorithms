package org.zzamba.p3649;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int x, n;
    static List<Integer> legos;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            x = sc.nextInt() * 10000000;
            n = sc.nextInt();
            legos = IntStream.range(0, n).map(i -> sc.nextInt()).boxed().collect(Collectors.toList());

            Collections.sort(legos);

            int s = 0, e = legos.size() - 1;
            boolean isFound = false;

            while (s < e) {
                int sum = legos.get(s) + legos.get(e);

                if (sum == x) {
                    System.out.println("yes " + legos.get(s) + " " + legos.get(e));
                    isFound = true;
                    break;
                }

                if (sum > x)
                    e--;
                if (sum < x)
                    s++;
            }

            if (!isFound)
                System.out.println("danger");
        }
    }
}

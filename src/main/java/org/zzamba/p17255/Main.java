package org.zzamba.p17255;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static String inp;
    static Set<String> visited;
    static int res;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        inp = sc.next();
        visited = new HashSet<>();
        res = 0;

        bruteforce(0, inp.length() - 1, "");

        System.out.println(res);
    }

    static void bruteforce(int s, int e, String seq) {
        seq += inp.substring(s, e + 1);

        if (s > e) {
            if (!visited.contains(seq)) {
                visited.add(seq);
                res++;
            }
            return;
        }

        bruteforce(s + 1, e, seq);
        bruteforce(s, e - 1, seq);
    }
}

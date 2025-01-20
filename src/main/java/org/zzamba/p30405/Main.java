package org.zzamba.p30405;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int k = sc.nextInt();
            int[] temp = IntStream.range(0, k).map(x -> sc.nextInt()).toArray();
            arr.add(temp[0]);
            arr.add(temp[k - 1]);
        }
        arr.sort(Comparator.naturalOrder());
        System.out.println(arr.get((arr.size() - 1) / 2));
    }
}

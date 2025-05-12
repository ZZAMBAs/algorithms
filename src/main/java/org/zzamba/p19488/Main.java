package org.zzamba.p19488;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int n, m, d, res;
    static int[] eCnt;
    static Queue<Integer> removed = new ArrayDeque<>();
    static List<List<Integer>> adv = new ArrayList<>();
    static boolean[] isRemoved;
    static List<Integer> ascRes;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        d = sc.nextInt();
        isRemoved = new boolean[n + 1];
        IntStream.rangeClosed(0, n).forEach(i -> adv.add(new ArrayList<>()));
        IntStream.range(0, m).forEach(i -> {
            int a = sc.nextInt();
            int b = sc.nextInt();

            adv.get(a).add(b);
            adv.get(b).add(a);
        });
        eCnt = IntStream.rangeClosed(0, n).map(i -> {
            int size = adv.get(i).size();

            if (i != 0 && size < d) {
                removed.add(i);
                isRemoved[i] = true;
            }

            return size;
        }).toArray();

        while (!removed.isEmpty()) {
            int front = removed.poll();

            eCnt[front] = Integer.MAX_VALUE;
            adv.get(front).forEach(v -> {
                eCnt[v]--;

                if (!isRemoved[v] && eCnt[v] < d) {
                    removed.add(v);
                    isRemoved[v] = true;
                }
            });
        }

        IntStream.rangeClosed(1, n).forEach(i -> {
            if (!isRemoved[i]) {
                List<Integer> asc = dfs(i, new ArrayList<>());

                if (asc.size() > res) {
                    res = asc.size();
                    ascRes = asc;
                }
            }
        });

        System.out.println(res > 0 ? res : "NIE");
        if (res > 0)
            System.out.print(ascRes.stream().sorted().map(String::valueOf).collect(Collectors.joining(" ")));
    }

    static List<Integer> dfs(int idx, List<Integer> list) {
        isRemoved[idx] = true;
        list.add(idx);

        for (int v : adv.get(idx)) {
            if (!isRemoved[v])
                list = dfs(v, list);
        }

        return list;
    }
}

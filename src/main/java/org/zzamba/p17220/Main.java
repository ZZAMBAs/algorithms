package org.zzamba.p17220;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int N, M, res;
    static List<List<Integer>> inDeg, outDeg;
    static boolean[] arrested;
    static boolean[] canReceive;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0;
        N = sc.nextInt();
        M = sc.nextInt();
        inDeg = new ArrayList<>();
        outDeg = new ArrayList<>();
        arrested = new boolean[N];
        canReceive = new boolean[N];
        IntStream.range(0, N).forEach(i -> {
            inDeg.add(new ArrayList<>());
            outDeg.add(new ArrayList<>());
        });
        IntStream.range(0, M).forEach(i -> {
            int s = sc.next().charAt(0) - 65;
            int e = sc.next().charAt(0) - 65;

            inDeg.get(e).add(s);
            outDeg.get(s).add(e);
        });
        int arrestCnt = sc.nextInt();

        IntStream.range(0, arrestCnt).forEach(i -> {
            int arrestNum = sc.next().charAt(0) - 65;
            arrested[arrestNum] = true;
        });

        List<Integer> bosses = findBosses();
        bosses.forEach(Main::checkReceiver);

        System.out.print(IntStream.range(0, N).filter(i -> canReceive[i]).count());
    }

    static List<Integer> findBosses() {
        return IntStream.range(0, N).filter(i -> inDeg.get(i).isEmpty()).boxed().collect(Collectors.toList());
    }

    static void checkReceiver(int boss) {
        if (arrested[boss])
            return;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(boss);
        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : outDeg.get(cur)) {
                if (arrested[next] || canReceive[next])
                    continue;

                q.add(next);
                canReceive[next] = true;
            }
        }

    }
}

package org.zzamba.p5638;

import java.util.*;
import java.util.stream.*;

public class Main {
    static int n, m, V, T;
    static long res;
    static List<Dam> dams;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        n = sc.nextInt();
        dams = IntStream.range(0, n).mapToObj(i -> new Dam(sc.nextInt(), sc.nextInt())).collect(Collectors.toList());
        m = sc.nextInt();
        IntStream.rangeClosed(1, m).forEach(i -> {
            V = sc.nextInt();
            T = sc.nextInt();
            res = Long.MAX_VALUE;

            bruteforce(0, new ArrayList<>());

            sb.append("Case ").append(i).append(": ").append(res == Long.MAX_VALUE ? "IMPOSSIBLE" : res).append("\n");
        });

        System.out.print(sb);
    }

    static void bruteforce(int idx, List<Dam> selectedDams) {
        if (idx == dams.size()) {
            if (selectedDams.isEmpty())
                return;

            long waterLimit = selectedDams.stream().mapToLong(dam -> dam.waterPerHour * T).sum();
            long sumCost = selectedDams.stream().mapToLong(dam -> dam.cost).sum();

            if (waterLimit >= V)
                res = Math.min(res, sumCost);

            return;
        }

        selectedDams.add(dams.get(idx));
        bruteforce(idx + 1, selectedDams);
        selectedDams.remove(selectedDams.size() - 1);
        bruteforce(idx + 1, selectedDams);
    }

    static class Dam {
        long waterPerHour;
        long cost;

        public Dam(long waterPerHour, long cost) {
            this.waterPerHour = waterPerHour;
            this.cost = cost;
        }
    }
}

package org.zzamba.p23030;

import java.util.*;
import java.util.stream.*;

public class Main {
    static final int INF = Integer.MAX_VALUE;
    static final Scanner sc = new Scanner(System.in);

    static int N, M, K;
    static int[][] dist;
    static Map<String, String> transfer = new HashMap<>();

    public static void main(String[] args) {
        init();

        List<Integer> res = IntStream.range(0, K).map(i -> dijkstra(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()))
            .boxed().collect(Collectors.toList());
        System.out.print(res.stream().map(String::valueOf).collect(Collectors.joining("\n")));
    }

    static int dijkstra(int T, int u1, int u2, int v1, int v2) {
        IntStream.range(1, N + 1).forEach(i -> Arrays.fill(dist[i], INF));
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(p -> p[2])); // {라인, 역, 비용}
        q.add(new int[]{u1, u2, 0});
        dist[u1][u2] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int curLine = cur[0];
            int curStation = cur[1];
            int curCost = cur[2];

            move(q, curLine, curStation, curCost + 1);

            Optional.ofNullable(transfer.get(getStationInfo(curLine, curStation)))
                .map(Main::getStationInfo)
                .ifPresent(arr -> {
                    if (dist[arr[0]][arr[1]] > curCost + T) {
                        dist[arr[0]][arr[1]] = curCost + T;
                        q.add(new int[]{arr[0], arr[1], curCost + T});
                    }
                });
        }

        return dist[v1][v2];
    }

    static void move(PriorityQueue<int[]> q, int line, int station, int cost) {
        int[] nextStations = new int[]{station + 1, station - 1};

        Arrays.stream(nextStations)
            .filter(s -> inRange(line, s) && dist[line][s] > cost)
            .forEach(s -> {
                dist[line][s] = cost;
                q.add(new int[]{line, s, cost});
            });
    }

    static boolean inRange(int line, int station) {
        if (station >= 0 && station < dist[line].length)
            return true;
        return false;
    }

    static void init() {
        N = sc.nextInt();
        dist = new int[N + 1][];
        int[] station = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        IntStream.range(0, N).forEach(i -> dist[i + 1] = new int[station[i] + 1]);
        M = sc.nextInt();
        IntStream.range(0, M).forEach(i -> {
            int P1 = sc.nextInt();
            int P2 = sc.nextInt();
            int Q1 = sc.nextInt();
            int Q2 = sc.nextInt();

            String P = getStationInfo(P1, P2);
            String Q = getStationInfo(Q1, Q2);

            transfer.put(P, Q);
            transfer.put(Q, P);
        });
        K = sc.nextInt();
    }

    static String getStationInfo(int line, int station) {
        StringBuilder sb = new StringBuilder();

        return sb.append(line)
            .append('-')
            .append(station)
            .toString();
    }

    static int[] getStationInfo(String info) {
        return Arrays.stream(info.split("-")).mapToInt(Integer::parseInt).toArray();
    }
}

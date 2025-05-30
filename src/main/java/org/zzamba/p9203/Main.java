package org.zzamba.p9203;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.time.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        IntStream.range(0, T).forEach(t -> {
            try {
                int maxRoom = 0;
                int curRoom = 0;
                String[] firstLine = br.readLine().split(" ");
                int B = Integer.parseInt(firstLine[0]);
                int C = Integer.parseInt(firstLine[1]);

                PriorityQueue<Pair<Long, Boolean>> pq = new PriorityQueue<>(Comparator.<Pair<Long, Boolean>, Long>comparing(Pair::getT).thenComparing(Pair::getR));

                IntStream.range(0, B).forEach(i -> {
                    try {
                        String[] reservation = br.readLine().split(" ");
                        String sDay = reservation[1];
                        String sTime = reservation[2];
                        String eDay = reservation[3];
                        String eTime = reservation[4];

                        addTimeline(pq, sDay, sTime, eDay, eTime, C);
                    } catch (IOException ignored) {
                    }
                });

                while (!pq.isEmpty()) {
                    var front = pq.poll();
                    curRoom += front.r ? 1 : -1;
                    maxRoom = Math.max(curRoom, maxRoom);
                }

                sb.append(maxRoom).append('\n');
            } catch (IOException ignored) {
            }

            if (t % 25 == 0)
                System.gc();
        });

        System.out.print(sb);
    }

    static void addTimeline(PriorityQueue<Pair<Long, Boolean>> pq, String sDay, String sTime, String eDay, String eTime, int C) {
        int[] sEle = Arrays.stream(sDay.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] eEle = Arrays.stream(eDay.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] sT = Arrays.stream(sTime.split(":")).mapToInt(Integer::parseInt).toArray();
        int[] eT = Arrays.stream(eTime.split(":")).mapToInt(Integer::parseInt).toArray();

        pq.add(new Pair<>(LocalDateTime.of(sEle[0], sEle[1], sEle[2], sT[0], sT[1]).toInstant(ZoneOffset.UTC).getEpochSecond(), true));
        pq.add(new Pair<>(LocalDateTime.of(eEle[0], eEle[1], eEle[2], eT[0], eT[1]).plusMinutes(C).toInstant(ZoneOffset.UTC).getEpochSecond(), false));
    }

    static class Pair<T, R> {
        T t;
        R r;

        Pair (T t, R r) {
            this.t = t;
            this.r = r;
        }

        T getT() {
            return t;
        }

        R getR() {
            return r;
        }
    }
}

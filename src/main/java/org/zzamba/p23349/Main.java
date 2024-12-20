package org.zzamba.p23349;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static int N;
    static Set<String> entry;
    static Map<String, PriorityQueue<Pair<Integer, Boolean>>> places;
    static int resSelected, resMax, resStart, resEnd;
    static String resPlace;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        entry = new HashSet<>();
        places = new TreeMap<>(Comparator.naturalOrder());
        resSelected = resMax = resStart = resEnd = 0;
        resPlace = "";
        IntStream.range(0, N).forEach(i -> {
            String name = sc.next();
            String place = sc.next();
            int start = sc.nextInt();
            int end = sc.nextInt();

            if (!entry.contains(name)) {
                entry.add(name);

                places.putIfAbsent(place, new PriorityQueue<>(Comparator.comparing(Pair::getT)));
                places.get(place).add(new Pair<>(start, false));
                places.get(place).add(new Pair<>(end, true));
            }
        });

        for (Map.Entry<String, PriorityQueue<Pair<Integer, Boolean>>> entry : places.entrySet()) {
            PriorityQueue<Pair<Integer, Boolean>> pq = entry.getValue();
            int cnt = 0, preCnt = 0;
            int maxS = pq.peek().t;
            int maxE = 0;
            while (!pq.isEmpty()) {
                int idx = pq.peek().t;
                while (true) {
                    if (pq.isEmpty() || pq.peek().t != idx)
                        break;

                    cnt += pq.poll().r ? -1 : 1;
                }

                if (preCnt < cnt)
                    maxS = idx;
                else if (preCnt > cnt && resMax < preCnt) {
                    maxE = idx;
                    resMax = preCnt;
                    resStart = maxS;
                    resEnd = maxE;
                    resPlace = entry.getKey();
                }

                preCnt = cnt;
            }
        }

        System.out.println(resPlace + " " + resStart + " " + resEnd);
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        public T getT() {
            return t;
        }
    }
}

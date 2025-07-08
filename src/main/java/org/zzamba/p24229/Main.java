package org.zzamba.p24229;

import java.util.*;
import java.util.stream.*;

// 1. s, e 정렬 후 판자 통합
// 2. 각 판자 별 점프 길이 포함해 실 길이 늘림
// 3. 첫 판자부터 길이 이내 판자들을 계속해서 선택.
public class Main {
    static Pair<Integer, Integer> lastPanza;
    static int onPanza;
    static List<Pair<Integer, Integer>> panza = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Pair<Integer, Boolean>> temp = IntStream.range(0, sc.nextInt())
            .boxed()
            .flatMap(i -> IntStream.range(0, 2)
                .mapToObj(j -> new Pair<>(sc.nextInt(), j == 0)))
            .sorted(Comparator.comparing(Pair::getT))
            .collect(Collectors.toList());

        int s = 0;

		for (Pair<Integer, Boolean> p : temp) {
            onPanza += p.r ? 1 : -1;

            if (onPanza == 1 && p.r)
                s = p.t;

            if (onPanza == 0 && !p.r)
                panza.add(new Pair<>(s, p.t));

        }

        cal();

        System.out.println(lastPanza.r);
    }

    static void cal() {
        int idx = 0;
        int maxLen = 0;

        while (idx < panza.size() && panza.get(idx).t <= maxLen) {
            lastPanza = panza.get(idx++);
            maxLen = Math.max(maxLen, 2 * lastPanza.r - lastPanza.t);
        }
    }

    static class Pair<T, R> {
        T t;
        R r;

        Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }

        T getT() {
            return t;
        }
    }
}

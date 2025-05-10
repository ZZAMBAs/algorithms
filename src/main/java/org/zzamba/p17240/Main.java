package org.zzamba.p17240;

import java.util.*;
import java.util.stream.*;

// 풀이 방법이 많은 듯.
public class Main {
    static List<List<Pair<Integer, Integer>>> arr = new ArrayList<>(); // 정렬된 포지션 값. {값, 후보자 idx}
    static int n;
    static List<Pair<Integer, Integer>> diff = new ArrayList<>(); // {차이, 삽입 idx}
    static int[] score = new int[5];
    static int[] arrItr = new int[5];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Arrays.fill(score, -1);
        IntStream.range(0, 5).forEach(i -> arr.add(new ArrayList<>()));
        n = sc.nextInt();
        IntStream.range(0, n).forEach(i -> diff.add(new Pair<>(-1, -1)));
        IntStream.range(0, n).forEach(i -> IntStream.range(0, 5).forEach(j -> arr.get(j).add(new Pair<>(sc.nextInt(), i))));
        IntStream.range(0, 5).forEach(i -> arr.get(i).sort(Comparator.<Pair<Integer, Integer>, Integer>comparing(Pair::getT).reversed().thenComparing(Pair::getR)));

        boolean sw = true;
        while (sw) {
            IntStream.range(0, 5).forEach(Main::fill);
            sw = check();
        }

        System.out.print(Arrays.stream(score).sum());
    }

    static void fill(int pos) {
        if (score[pos] >= 0)
            return;

        int curPos = arrItr[pos];
        List<Pair<Integer, Integer>> curArr = arr.get(pos);
        int val = curArr.get(curPos).t;
        int selected = curArr.get(curPos).r;

        if (curPos == curArr.size() - 1 || diff.get(selected).t == -1) { // 마지막이거나 방문 없으면
            score[pos] = val;
            diff.set(selected, new Pair<>(curPos == curArr.size() - 1 ? 0 : val - curArr.get(curPos + 1).t, pos));
        } else {

            int curDiff = val - curArr.get(curPos + 1).t;
            int savedDiff = diff.get(selected).t;

            if (curDiff > savedDiff) {
                score[diff.get(selected).r] = -1; // 기존 점수 무효화
                score[pos] = val;
                diff.set(selected, new Pair<>(curDiff, pos));
            }
        }

        arrItr[pos]++;
    }

    static boolean check() {
        // System.out.println(Arrays.stream(score).mapToObj(String::valueOf).collect(Collectors.joining(" ")));

        for (int i = 0; i < 5; i++)
            if (score[i] < 0)
                return true;
        return false;
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

        R getR() {
            return r;
        }
    }
}

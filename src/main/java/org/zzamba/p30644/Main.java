package org.zzamba.p30644;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    static int N;
    static List<Pair> nums;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        nums = IntStream.range(0, N).mapToObj(i -> new Pair(sc.nextInt(), i)).sorted(Comparator.comparing(Pair::getX)).collect(Collectors.toList());

        System.out.print(getCuttingNum());
    }

    static int getCuttingNum() {
        int ret = 0;
        int curIdx = nums.get(0).y;

        for (int i = 1; i < nums.size(); i++) {
            int nextIdx = nums.get(i).y;

            if (Math.abs(nextIdx - curIdx) > 1)
                ret++;

            curIdx = nextIdx;
        }

        return ret;
    }

    static class Pair {
        int x;
        int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }
    }
}

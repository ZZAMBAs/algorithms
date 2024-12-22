package org.zzamba.p9320;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Main {
    static int T;
    static Res[] nums = new Res[4];
    static Oper[] allOpers = {Oper.PLUS, Oper.MINUS, Oper.MULTIPLY, Oper.DIVIDE};
    static Oper[] opers = new Oper[3];
    static boolean isOk;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt();
        while (T-- > 0) {
            isOk = false;
            nums = IntStream.range(0, 4).mapToObj(i -> new Res(1, sc.nextInt())).toArray(Res[]::new);
            boolean[] isVisited = new boolean[4];
            bruteforce(0, new Res[4], isVisited);
            System.out.println(isOk ? "YES" : "NO");
        }
    }

    static void bruteforce(int idx, Res[] realNum, boolean[] isVisited) {
        if (idx == 4) {
            operBruteforce(0, realNum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (isVisited[i])
                continue;

            isVisited[i] = true;
            realNum[idx] = nums[i];
            bruteforce(idx + 1, realNum, isVisited);
            isVisited[i] = false;
        }
    }

    static void operBruteforce(int idx, Res[] realNum) {
        if (idx == 3) {
            for (int i = 0; i < 5; i++)
                cal(i, realNum);
            return;
        }

        for (Oper oper : allOpers) {
            opers[idx] = oper;
            operBruteforce(idx + 1, realNum);
        }
    }

    static void cal(int methodNum, Res[] realNum) {
        Res val = switch (methodNum) {
            case 0 -> opers[2].cal(opers[1].cal(opers[0].cal(realNum[0], realNum[1]), realNum[2]), realNum[3]);
            case 1 -> opers[1].cal(opers[0].cal(realNum[0], realNum[1]), opers[2].cal(realNum[2], realNum[3]));
            case 2 -> opers[2].cal(opers[0].cal(realNum[0], opers[1].cal(realNum[1], realNum[2])), realNum[3]);
            case 3 -> opers[0].cal(realNum[0], opers[2].cal(opers[1].cal(realNum[1], realNum[2]), realNum[3]));
            case 4 -> opers[0].cal(realNum[0], opers[1].cal(realNum[1], opers[2].cal(realNum[2], realNum[3])));
            default -> new Res(1, 0);
        };

        if (is24(val))
            isOk = true;
    }

    static class Res {
        int par;
        int child;

        public Res(int par, int child) {
            this.par = par;
            this.child = child;

            if (child % par == 0) {
                this.child /= par;
                this.par = 1;
            }
        }
    }

    static boolean is24(Res res) {
        return res.child == 24 && res.par == 1;
    }

    enum Oper {
        PLUS((a, b) -> new Res(a.par * b.par, a.child * b.par + b.child * a.par)),
        MINUS((a, b) -> new Res(a.par * b.par, a.child * b.par - b.child * a.par)),
        MULTIPLY((a, b) -> new Res(a.par * b.par, a.child * b.child)),
        DIVIDE((a, b) -> {
            if (b.child == 0)
                return new Res(1, 0);
            return MULTIPLY.cal(a, new Res(b.child, b.par));
        });

        private final BiFunction<Res, Res, Res> f;

        Oper(BiFunction<Res, Res, Res> f) {
            this.f = f;
        }

        Res cal(Res a, Res b) {
            return f.apply(a, b);
        }
    }
}


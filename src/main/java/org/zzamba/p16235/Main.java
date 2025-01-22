package org.zzamba.p16235;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static final int INIT_NUTRIENT = 5;

    static int[][] dl = { {1, 0}, {1, 1} , {0, 1}, {0, -1}, {-1, 0}, {-1, -1}, {1, -1}, {-1, 1} };
    static int N, M, K;
    static int[][] nutrients, A;
    static List<List<PriorityQueue<Integer>>> trees;
    static Deque<Pair<Integer, Integer>> treesMultiple5;

    public static void main(String[] args) throws Exception {
        init();
        liveForK();
        System.out.print(countLivingTrees());
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        trees = new ArrayList<>();
        treesMultiple5 = new ArrayDeque<>();
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inp[0];
        M = inp[1];
        K = inp[2];
        nutrients = new int[N][N];
        A = new int[N][N];
        for (int i = 0; i < N; i++) {
            A[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            Arrays.fill(nutrients[i], INIT_NUTRIENT);
            trees.add(new ArrayList<>());
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                trees.get(i).add(new PriorityQueue<>());
        for (int i = 0; i < M; i++) {
            int[] treeInp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            trees.get(treeInp[0] - 1).get(treeInp[1] - 1).add(treeInp[2]);
        }

    }

    static void liveForK() {
        IntStream.range(0, K).forEach(k -> {
            liveUntilSummer();
            liveFall();
            liveWinter();
        });
    }

    static void liveUntilSummer() {
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                List<Integer> deadTrees = new ArrayList<>();
                PriorityQueue<Integer> pq = trees.get(i).get(j);
                PriorityQueue<Integer> nextPq = new PriorityQueue<>();

                while (!pq.isEmpty()) {
                    int curTreeAge = pq.poll();

                    if (curTreeAge > nutrients[i][j]) {
                        deadTrees.add(curTreeAge);
                        continue;
                    }

                    nutrients[i][j] -= curTreeAge;
                    nextPq.add(curTreeAge + 1);
                    if ((curTreeAge + 1) % 5 == 0)
                        treesMultiple5.push(new Pair<>(i, j));
                }

                trees.get(i).set(j, nextPq);

                for (int age : deadTrees)
                    nutrients[i][j] += age / 2;
            }

        }
    }

    static void liveFall() {
        while (!treesMultiple5.isEmpty()) {
            Pair<Integer, Integer> pair = treesMultiple5.pop();

            for (int[] d : dl) {
                int nextR = pair.t + d[0];
                int nextC = pair.r + d[1];

                if (inRange(nextR, nextC))
                    trees.get(nextR).get(nextC).add(1);
            }
        }
    }

    static void liveWinter() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                nutrients[i][j] += A[i][j];
    }

    static boolean inRange(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    static int countLivingTrees() {
        int ret = 0;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                ret += trees.get(i).get(j).size();

        return ret;
    }

    static class Pair<T, R> {
        T t;
        R r;

        public Pair(T t, R r) {
            this.t = t;
            this.r = r;
        }
    }
}

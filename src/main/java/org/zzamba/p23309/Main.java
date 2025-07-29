package org.zzamba.p23309;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static int N, M;
    static StringBuilder sb = new StringBuilder();
    static int[] nexts = new int[1000001];
    static int[] pres = new int[1000001];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] inp = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
        N = inp[0];
        M = inp[1];
        int[] inp2 = Arrays.stream(br.readLine().split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();
        IntStream.range(0, N).forEach(i -> {
            nexts[inp2[i]] = inp2[i];
            pres[inp2[i]] = inp2[i];

            if (i != 0) {
                pres[inp2[i]] = inp2[i - 1];
                connect(inp2[i - 1], inp2[i]);
            }
        });

        IntStream.range(0, M).forEach(i -> {
            try {
                String[] cmd = br.readLine().split(" ");

                switch (cmd[0]) {
                    case "BN" -> {
                        sb.append(nexts[Integer.parseInt(cmd[1])]).append('\n');
                        connect(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
                    }
                    case "BP" -> {
                        sb.append(pres[Integer.parseInt(cmd[1])]).append('\n');
                        connect(pres[Integer.parseInt(cmd[1])], Integer.parseInt(cmd[2]));
                    }
                    case "CN" -> {
                        sb.append(nexts[Integer.parseInt(cmd[1])]).append('\n');
                        disconnect(Integer.parseInt(cmd[1]), nexts[Integer.parseInt(cmd[1])]);
                    }
                    case "CP" -> {
                        sb.append(pres[Integer.parseInt(cmd[1])]).append('\n');
                        disconnect(pres[pres[Integer.parseInt(cmd[1])]], pres[Integer.parseInt(cmd[1])]);
                    }
                }
            } catch (Exception e) {}
        });

        System.out.print(sb);
    }

    static void connect(int pre, int next) {
        nexts[next] = nexts[pre];
        pres[next] = pre;
        pres[nexts[pre]] = next;
        nexts[pre] = next;
    }

    static void disconnect(int pre, int next) {
        nexts[pre] = nexts[next];
        pres[nexts[next]] = pre;
    }
}

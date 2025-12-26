package org.zzamba.p10800;

import java.util.*;
import java.io.*;
import java.util.stream.*;

// temp는 같은 크기일 경우 업데이트 지연을 위해 도입했지만, 이것 없이 따로 투 포인터로 지연을 관리할 수 있다.
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int sum, N, tempSum;
    static int[] colorSum, res;
    static Map<Integer, Integer> temp = new HashMap<>();
    static Triple[] inputs;

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        inputs = new Triple[N];
        colorSum = new int[N + 1];
        res = new int[N];

        for (int i = 0; i < N; i++) {
            int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            inputs[i] = new Triple(i, line[0], line[1]);
        }
        Arrays.sort(inputs, Comparator.comparing(Triple::getSize));
        Triple first = inputs[0];
        temp.put(first.color, first.size);
        tempSum += first.size;

        for (int i = 1; i < N; i++) {
            Triple preP = inputs[i - 1];
            Triple curP = inputs[i];

            if (preP.size != curP.size) {
                sum += tempSum;
                for (Map.Entry<Integer, Integer> e : temp.entrySet())
                    colorSum[e.getKey()] += e.getValue();

                temp.clear();
                tempSum = 0;
            }

            temp.putIfAbsent(curP.color, 0);
            temp.put(curP.color, temp.get(curP.color) + curP.size);
            tempSum += curP.size;

            res[curP.idx] = sum - colorSum[curP.color];
        }

        System.out.print(Arrays.stream(res).mapToObj(String::valueOf).collect(Collectors.joining("\n")));
    }

    static class Triple {
        int idx;
        int color;
        int size;

        Triple(int idx, int color, int size) {
            this.idx = idx;
            this.color = color;
            this.size = size;
        }

        int getSize() {
            return size;
        }
    }
}

package org.zzamba.p25634;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] bulbs = IntStream.range(0, N).map(i -> sc.nextInt()).toArray();
        int res = Arrays.stream(bulbs).sum();
        for (int i = 0; i < N; i++) {
            int light = sc.nextInt();
            bulbs[i] *= (light == 1) ? -1 : 1;

            if (light == 0)
                res -= bulbs[i];
        }
        int maxChange = 0;
        int curChange = 0;

        int s = 0, e = 0;
        while (e < N) {
            curChange += bulbs[e++];

            while (curChange <= 0 && s < e)
                curChange -= bulbs[s++];

            maxChange = Math.max(maxChange, curChange);
        }
        maxChange = Math.max(maxChange, curChange);

        if (maxChange == 0)
            res += Arrays.stream(bulbs).max().getAsInt();
        else
            res += maxChange;

        System.out.print(res);
    }
}

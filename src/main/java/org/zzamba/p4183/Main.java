package org.zzamba.p4183;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int res = 0;
            Map<Integer, Integer> map = new HashMap<>();

            List<Integer> snows = IntStream.range(0, N).map(i -> {
                int ret = 0;
                try {
                    ret = Integer.parseInt(br.readLine());
                } catch (Exception e) {}
				return ret;
			}).boxed().collect(Collectors.toList());

            int s = 0, e = 0;
            while (e < snows.size()) {
                int curSnow = snows.get(e);

                map.putIfAbsent(curSnow, 0);
                map.put(curSnow, map.get(curSnow) + 1);

                while (map.get(curSnow) > 1) {
                    int sSnow = snows.get(s++);
                    map.put(sSnow, map.get(sSnow) - 1);
                }

                res = Math.max(res, e++ - s + 1);
            }

            sb.append(res).append("\n");
        }

        System.out.print(sb);
    }
}

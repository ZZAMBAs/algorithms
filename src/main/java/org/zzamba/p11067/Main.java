package org.zzamba.p11067;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static final int MAX_X_LIMIT = 100001;
    static List<List<Integer>> cafes;
    static List<Integer> findingCafes;
    static List<String> cntCoor;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int T = sc.nextInt();
        while (T-- > 0) {
            cafes = new ArrayList<>();
            findingCafes = new ArrayList<>();
            cntCoor = new ArrayList<>();
            IntStream.range(0, MAX_X_LIMIT).forEach(i -> cafes.add(new ArrayList<>()));
            IntStream.range(0, sc.nextInt()).forEach(i -> cafes.get(sc.nextInt()).add(sc.nextInt()));
            cafes.forEach(cafe -> cafe.sort(Comparator.naturalOrder()));

            int m = sc.nextInt();
            IntStream.range(0, m).forEach(i -> findingCafes.add(sc.nextInt() - 1));

            int y = 0;

            for (int x = 0; x < MAX_X_LIMIT; x++) {
                List<Integer> curXCafes = cafes.get(x);

                if (curXCafes.isEmpty())
                    continue;

                int w = curXCafes.get(0) == y ? 1 : -1;

                for (int idx = ((w == 1) ? 0 : curXCafes.size() - 1); idx >= 0 && idx < curXCafes.size(); idx += w) {
                    if (findingCafes.isEmpty())
                        break;

                    y = curXCafes.get(idx);
                    cntCoor.add(x + " " + y);
                }
            }

            for (int idx : findingCafes)
                sb.append(cntCoor.get(idx)).append('\n');
        }

        System.out.print(sb);
    }
}

package org.zzamba.p20210;

import java.util.*;
import java.util.stream.*;

// CustomChar 정렬 기준
// 1. 숫자가 문자보다 우선
// 2. 숫자의 경우, 작은 수이면서 앞에 0이 붙은 개수가 적은 것이 우선
// 3. 문자 우선순위는 "AaBb...Zz"를 따름
public class Main {
    static List<CustomChar> list;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        list = IntStream.range(0, N).mapToObj(i -> sc.next())
            .map(Main::split)
            .collect(Collectors.toList());
        Collections.sort(list);
        list.forEach(l ->
            System.out.println(String.join("", l.strs))
        );
    }

    static CustomChar split(String s) {
        String[] arr = s.split("");
        List<String> strs = Arrays.stream(arr).collect(
            ArrayList::new,
            (list, str) -> {
                if (!list.isEmpty() && str.charAt(0) < 65 && list.get(list.size() - 1).charAt(0) < 65) {
                    String nStr = list.get(list.size() - 1) + str;
                    list.remove(list.size() - 1);
                    list.add(nStr);
                }
                else
                    list.add(str);
            },
            ArrayList::addAll
        );

        return new CustomChar(strs);
    }

    static class CustomChar implements Comparable<CustomChar>{
        List<String> strs;
        List<Integer> zeroCnt;

        CustomChar(List<String> strs) {
            this.strs = strs;
            this.zeroCnt = strs.stream().map(str ->
                (int)IntStream.range(0, str.length())
                    .takeWhile(i -> str.charAt(i) == '0')
                    .count()
            ).collect(Collectors.toList());
        }

        @Override
        public int compareTo(CustomChar o) {
            String charCmp = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

            for (int i = 0; i < Math.min(this.strs.size(), o.strs.size()); i++) {
                String curThis = this.strs.get(i);
                int curThisZ = this.zeroCnt.get(i);
                String curO = o.strs.get(i);
                int curOZ = o.zeroCnt.get(i);
                char thisFir = curThis.charAt(0);
                char oFir = curO.charAt(0);

                if (thisFir < 65 && oFir >= 65)
                    return -1;
                if (thisFir >= 65 && oFir < 65)
                    return 1;
                if (thisFir >= 65 && thisFir != oFir)
                    return charCmp.indexOf(thisFir) > charCmp.indexOf(oFir) ? 1 : -1;
                if (thisFir < 65) { // 둘 다 숫자
                    int cmp = cmp(curThis, curO, curThisZ, curOZ);

                    if (cmp != 0)
                        return cmp;
                }

            }

            if (this.strs.size() > o.strs.size())
                return 1;
            else
                return this.strs.size() < o.strs.size() ? -1 : 0;
        }

        int cmp(String s1, String s2, int s1ZeroCnt, int s2ZeroCnt) {
			if (s1.length() - s1ZeroCnt > s2.length() - s2ZeroCnt)
                return 1;
            if (s1.length() - s1ZeroCnt < s2.length() - s2ZeroCnt)
                return -1;

            for (int i = 0; i < Math.min(s1.length() - s1ZeroCnt, s2.length() - s2ZeroCnt); i++) {
                if (s1.charAt(s1ZeroCnt + i) < s2.charAt(s2ZeroCnt + i))
                    return -1;
                if (s1.charAt(s1ZeroCnt + i) > s2.charAt(s2ZeroCnt + i))
                    return 1;
            }

            if (s1ZeroCnt != s2ZeroCnt)
                return s1ZeroCnt > s2ZeroCnt ? 1 : -1;
            return 0;
        }
    }
}

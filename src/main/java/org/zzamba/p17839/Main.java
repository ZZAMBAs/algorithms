package org.zzamba.p17839;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> adj = new HashMap<>();
        Set<String> visited = new HashSet<>();
        List<String> res = new ArrayList<>();
        int N = Integer.parseInt(sc.nextLine());
        IntStream.range(0, N).forEach(i -> {
            String[] inp = sc.nextLine().split(" ");

            adj.putIfAbsent(inp[0], new ArrayList<>());
            adj.get(inp[0]).add(inp[2]);
        });

        dfs("Baba", adj, visited, res);

        System.out.println(res.stream().sorted().collect(Collectors.joining("\n")));
    }

    static void dfs(String s, Map<String, List<String>> adj, Set<String> visited, List<String> res) {
        if (!s.equals("Baba"))
            res.add(s);

        if (!adj.containsKey(s))
            return;

        adj.get(s).forEach(adv -> {
            if (!visited.contains(adv)) {
                visited.add(adv);
                dfs(adv, adj, visited, res);
            }
        });
    }
}

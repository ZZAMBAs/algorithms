package org.zzamba.p5021;

import java.util.*;
import java.io.*;

public class Main {
    static final long LORD_ENERGY = 1L << 55;

    static int N, M;
    static String lord;
    static List<String> candidates = new ArrayList<>();
    static Map<String, Long> personEnergy = new HashMap<>();
    static Map<String, String[]> par = new HashMap<>();
    static Map<String, List<String>> children = new HashMap<>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = inp[0];
        M = inp[1];
        lord = br.readLine();
        personEnergy.put(lord, LORD_ENERGY);

        while (N-- > 0) {
            String[] parentInfos = br.readLine().split(" ");

            String[] pars = new String[]{parentInfos[1], parentInfos[2]};
            Arrays.sort(pars);

            String par1 = pars[0];
            String par2 = pars[1];
            String child = parentInfos[0];

            par.put(child, new String[]{par1, par2});

            children.computeIfAbsent(par1, k -> new ArrayList<>());
            children.computeIfAbsent(par2, k -> new ArrayList<>());

            children.get(par1).add(child);
            children.get(par2).add(child);
        }

        while (M-- > 0)
            candidates.add(br.readLine());

        System.out.print(getNextLord());
    }

    static String getNextLord() {
        PriorityQueue<Pair> pq = new PriorityQueue(Comparator.comparing(Pair::getEnergy).reversed());

        for (String name : candidates) {
            long val = getEnergy(name);
            // System.out.println("name: " + name + ", val: " + Long.toBinaryString(val));
            pq.add(new Pair(name, val));
        }

        return pq.poll().name;
    }

    static long getEnergy(String name) {
        if (personEnergy.get(name) != null)
            return personEnergy.get(name);

        String[] pars = par.get(name);
        if (pars != null)
            personEnergy.put(name, (getEnergy(pars[0]) >> 1) + (getEnergy(pars[1]) >> 1));
        else
            personEnergy.put(name, 0L);

        return personEnergy.get(name);
    }

    static class Pair {
        long energy;
        String name;

        Pair (String name, long energy) {
            this.energy = energy;
            this.name = name;
        }

        long getEnergy() {
            return energy;
        }
    }
}

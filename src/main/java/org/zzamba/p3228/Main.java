package org.zzamba.p3228;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static List<Coordinate> restaurants;

    static int K;
    static int R;
    static int M;
    static int res;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();
        R = sc.nextInt();
        M = sc.nextInt();
        res = 0;

        restaurants = IntStream.range(0, M)
            .mapToObj(i -> new Coordinate(sc.nextInt(), sc.nextInt()))
            .collect(Collectors.toList());

        int N = sc.nextInt();

        List<Solitaire> solitaires = IntStream.range(0, N)
            .mapToObj(i -> new Solitaire(sc.nextInt(), sc.nextInt(), sc.nextInt()))
            .collect(Collectors.toList());

        bruteforce(0, M, new ArrayList<>(), solitaires);

        System.out.println(res);
    }

    static void bruteforce(int idx, int end, List<Coordinate> selectedRestaurants, List<Solitaire> solitaires) {
        if (idx == end) {
            res = Math.max(res, solitaires.stream().mapToInt(solitaire -> cal(solitaire, selectedRestaurants)).sum());
            return;
        }

        if (selectedRestaurants.size() < K) {
            selectedRestaurants.add(restaurants.get(idx));
            bruteforce(idx + 1, end, selectedRestaurants, solitaires);
            selectedRestaurants.remove(selectedRestaurants.size() - 1);
        }

        bruteforce(idx + 1, end, selectedRestaurants, solitaires);
    }

    static int cal(Solitaire solitaire, List<Coordinate> selectedRestaurants) {
        for (Coordinate restaurant : selectedRestaurants)
            if (isOk(solitaire, restaurant))
                return solitaire.num;
        return 0;
    }

    static boolean isOk(Solitaire solitaire, Coordinate restaurant) {
        int xDiff = Math.abs(solitaire.x - restaurant.x);
        int yDiff = Math.abs(solitaire.y - restaurant.y);

        return (xDiff * xDiff) + (yDiff * yDiff) <= R * R;
    }

    static class Coordinate {
        int x;
        int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Solitaire extends Coordinate {
        int num;

        public Solitaire(int x, int y, int num) {
            super(x, y);
            this.num = num;
        }
    }
}

package org.zzamba.p2374;

import java.util.Scanner;
import java.util.stream.IntStream;

// (스택) 그리디 알고리즘으로 풀이도 가능하다.
public class Main {
    static int n;
    static long res;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        res = 0L;
        n = sc.nextInt();
        int[] inputs = IntStream.range(0, n).map(i -> sc.nextInt()).toArray();
        Node header = new Node(Integer.MAX_VALUE, null, null);
        Node firstNode = new Node(inputs[0], header, null);
        header.next = firstNode;
        Node curNode = firstNode;
        for (int v : inputs) {
            if (curNode.val == v)
                continue;

            Node newNode = new Node(v, curNode, null);
            curNode.next = newNode;
            curNode = newNode;
        }

        while (header.next.next != null) {
            int minVal = Integer.MAX_VALUE;
            Node iterNode = header.next;

            while (iterNode != null) {
                minVal = Math.min(minVal, iterNode.val);
                iterNode = iterNode.next;
            }

            iterNode = header.next;

            while (iterNode != null) {

                if (minVal == iterNode.val) {

                    Node preNode = iterNode.pre;
                    Node nextNode = iterNode.next;

                    int preVal = preNode == null ? Integer.MAX_VALUE : preNode.val;
                    int nextVal = nextNode == null ? Integer.MAX_VALUE : nextNode.val;

                    if (preVal > nextVal) {
                        res += nextVal - iterNode.val;

                        nextNode.pre = preNode;
                        preNode.next = nextNode;
                        iterNode.pre = iterNode.next = null;

                        iterNode = nextNode;
                    } else if (preVal < nextVal) {
                        res += preVal - iterNode.val;

                        preNode.next = nextNode;
                        if (nextNode != null)
                            nextNode.pre = preNode;
                        iterNode.pre = iterNode.next = null;

                        iterNode = preNode.next;
                    } else {
                        res += nextVal - iterNode.val;

                        preNode.next = nextNode.next;
                        if (nextNode.next != null)
                            nextNode.next.pre = preNode;
                        nextNode.next = nextNode.pre = null;
                        iterNode.next = iterNode.pre = null;

                        iterNode = preNode.next;
                    }
                }
                else
                    iterNode = iterNode.next;
            }
        }

        System.out.println(res);
    }

    static class Node {
        int val;
        Node pre;
        Node next;

        public Node(int val, Node pre, Node next) {
            this.val = val;
            this.pre = pre;
            this.next = next;
        }
    }
}

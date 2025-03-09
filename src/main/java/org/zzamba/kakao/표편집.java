package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

public class Solution {
	static List<Node> nodes = new ArrayList<>();
	static Deque<Node> stk = new ArrayDeque<>();
	static Node curNode = null;

	public String solution(int n, int k, String[] cmd) {
		StringBuilder sb = new StringBuilder();

		Node header = new Node(-1, null, null);
		Node tail = new Node(-1, null, null);
		insertBefore(tail, header);

		IntStream.range(0, n).forEach(i -> {
			Node newNode = new Node(i, null, null);
			nodes.add(newNode);
			insertBefore(tail, newNode);
		});
		curNode = header.postNode;
		for (int i = 0; i < k; i++)
			curNode = curNode.postNode;

		for (String c : cmd) {
			String[] parsing = c.split(" ");
			switch(parsing[0]) {
				case "D" -> move(Integer.parseInt(parsing[1]));
				case "U" -> move(-Integer.parseInt(parsing[1]));
				case "C" -> clear();
				case "Z" -> rollback();
			}
		}

		curNode = header.postNode;
		int nowIdx = 0;
		boolean[] having = new boolean[n];
		while (curNode != tail) {
			having[curNode.idx] = true;
			curNode = curNode.postNode;
		}
		IntStream.range(0, n).forEach(i -> sb.append(having[i] ? "O" : "X"));

		return sb.toString();
	}

	void insertBefore(Node node, Node newNode) {
		Node aNode = node.preNode;

		if (aNode != null)
			aNode.postNode = newNode;
		node.preNode = newNode;
		newNode.preNode = aNode;
		newNode.postNode = node;
	}

	void clear() {
		System.out.println("clear: " + curNode.idx + ", pre: " + curNode.preNode.idx + ", post: " + curNode.postNode.idx);
		Node cl = nodes.get(curNode.idx);
		Node aNode = curNode.preNode;
		Node cNode = curNode.postNode;

		aNode.postNode = cNode;
		cNode.preNode = aNode;

		curNode = cNode.postNode == null ? aNode : cNode;

		stk.push(cl);
	}

	void rollback() {
		Node rNode = stk.pop();
		System.out.println("rollback: " + rNode.idx);

		insertBefore(rNode.postNode, rNode);
	}

	void move(int w) {
		System.out.println("before moving: " + curNode.idx);
		if (w < 0)
			for (int i = 0; i < -w; i++)
				curNode = curNode.preNode;
		else
			for (int i = 0; i < w; i++)
				curNode = curNode.postNode;

		System.out.println("moved Node: " + curNode.idx);
	}

	static class Node {
		int idx;
		Node preNode;
		Node postNode;

		Node(int idx, Node preNode, Node postNode) {
			this.idx = idx;
			this.preNode = preNode;
			this.postNode = postNode;
		}
	}
}

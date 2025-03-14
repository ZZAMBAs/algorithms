package org.zzamba.kakao;

import java.util.*;
import java.util.stream.*;

// 최초 풀이. O(height * N). 하지만 O(NlogN)풀이가 존재.
class Solution {
	static final int MAX_LEVEL = 1002;

	static int preOrderLast = 0, postOrderLast = 0;
	static int[] preOrder, postOrder;
	static List<Node> sortedNodes;
	static List<List<Node>> levelSortedNodes = new ArrayList<>();
	static Node root;

	public int[][] solution(int[][] nodeinfo) {
		preOrder = new int[nodeinfo.length];
		postOrder = new int[nodeinfo.length];

		sortedNodes = IntStream.range(0, nodeinfo.length)
			.mapToObj(i -> new Node(nodeinfo[i][0], nodeinfo[i][1], i + 1))
			.sorted(Comparator.comparing(Node::getY).reversed().thenComparing(Node::getX))
			.collect(Collectors.toList());
		IntStream.range(0, MAX_LEVEL).forEach(i -> levelSortedNodes.add(new ArrayList<>()));
		root = sortedNodes.get(0);

		filter();
		match();
		pre(root);
		post(root);

		return new int[][]{preOrder, postOrder};
	}

	void filter() {
		int curH = 0;
		int curY = sortedNodes.get(0).y;

		for (int i = 0; i < sortedNodes.size(); i++) {
			Node cur = sortedNodes.get(i);
			if (cur.y != curY) {
				curH++;
				curY = cur.y;
			}

			levelSortedNodes.get(curH).add(cur);
		}
	}

	void match() {
		int end = 0;
		for (int i = 0; i < MAX_LEVEL; i++)
			if (levelSortedNodes.get(i).isEmpty()) {
				end = i;
				break;
			}

		int parLv = 0;
		while (parLv < end - 1) {
			connect(parLv, parLv + 1);
			parLv++;
		}
	}

	void connect(int parL, int chL) {
		int parPtr = 0;
		int chPtr = 0;

		while (chPtr < levelSortedNodes.get(chL).size()) {
			Node parCand = levelSortedNodes.get(parL).get(parPtr);
			Node chCand = levelSortedNodes.get(chL).get(chPtr);

			if (canMatch(parCand.par, parCand, chCand.x)) {
				chCand.par = parCand;

				if (chCand.x < parCand.x)
					parCand.left = chCand;
				else
					parCand.right = chCand;

				chPtr++;
			}
			else {
				parPtr++;
			}
		}
	}

	boolean canMatch(Node par, Node ch, int x) {
		if (par == null)
			return true;

		if (par.left == ch) {
			if (x > par.x)
				return false;
		}
		else {
			if (x < par.x)
				return false;
		}

		return canMatch(par.par, par, x);
	}

	void pre(Node cur) {
		preOrder[preOrderLast++] = cur.seq;

		if (cur.left != null)
			pre(cur.left);
		if (cur.right != null)
			pre(cur.right);
	}

	void post(Node cur) {
		if (cur.left != null)
			post(cur.left);
		if (cur.right != null)
			post(cur.right);

		postOrder[postOrderLast++] = cur.seq;
	}

	static class Node {
		Node par;
		Node left;
		Node right;
		int x;
		int y;
		int seq;

		Node(int x, int y, int seq) {
			this.x = x;
			this.y = y;
			this.seq = seq;
		}

		int getX() {
			return x;
		}

		int getY() {
			return y;
		}
	}
}

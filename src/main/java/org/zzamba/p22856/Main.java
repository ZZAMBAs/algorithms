package org.zzamba.p22856;

import java.util.*;
import java.util.stream.*;

public class Main {
	static Tree[] trees;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		trees = IntStream.range(0, N + 1).mapToObj(Tree::new).toArray(Tree[]::new);
		IntStream.range(0, N).forEach(i -> {
			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();

			connect(a, b, c);
		});

		System.out.print((aproximateInorder(trees[1], inorder(trees[1])) & 0b01111111111111111111111111));
	}

	static int aproximateInorder(Tree t, Tree lastT) { // 해당 노드를 루트로 하는 트리의 이동 횟수
		int ret = 0;
		Tree[] subT = new Tree[]{t.left, t.right};
		for (Tree sub : subT) {
			if (sub != null) {
				ret++; // 무조건 이동은 해야 함.
				int subRet = aproximateInorder(sub, lastT);

				ret += (subRet & 0b01111111111111111111111111);
				if ((subRet & (1 << 31)) == 0)
					ret++; // 마지막 노드를 포함하지 않으면 돌아와야 함
				ret |= (subRet & (1 << 31));
			}
		}

		if (t == lastT)
			ret |= (1 << 31); // 최상단 비트는 해당 서브트리가 마지막 노드를 포함하는지를 표시함

		return ret;
	}

	static Tree inorder(Tree t) {
		if (t == null)
			return null;

		Tree rightInorder = inorder(t.right);

		if (rightInorder == null)
			return t;
		return rightInorder;
	}

	static void connect(int p, int l, int r) {
		if (l != -1) {
			trees[p].left = trees[l];
			trees[l].par = trees[p];
		}

		if (r != -1) {
			trees[p].right = trees[r];
			trees[r].par = trees[p];
		}
	}

	static class Tree {
		int id;
		Tree par;
		Tree left;
		Tree right;

		Tree(int id) {this.id = id;}
	}
}

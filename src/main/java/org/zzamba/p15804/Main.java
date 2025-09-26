package org.zzamba.p15804;

import java.util.*;
import java.io.*;

public class Main {

	static int n, m, curTime, lastLoc, maxBusOut;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		int[] inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		n = inp[0];
		m = inp[1];

		while (m-- > 0) {

			inp = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			curTime = Math.max(curTime, inp[0]);

			out();
			if (lastLoc == n) {
				lastLoc = 0;
				curTime = maxBusOut;
				maxBusOut = 0;
			}

			in(inp[1]);
		}

		System.out.print(lastLoc);
	}

	static void out() {
		if (maxBusOut <= curTime) {
			lastLoc = 0;
		}
	}

	static void in(int t) {
		lastLoc++;
		maxBusOut = Math.max(maxBusOut, curTime + t);
	}
}

import java.util.*;
import java.util.stream.*;

public class Main{
    static final Scanner sc = new Scanner(System.in);
    static final int MOD_NUM = 1000000009;
    
    static int N, M, res;
    static int[][] dp;
    static List<String> map;
    
	public static void main(String[] args) {
		N = sc.nextInt();
		M = sc.nextInt();
		dp = new int[N + 1][M + 1];
		map = IntStream.range(0, N).mapToObj(i -> sc.next()).collect(Collectors.toList());
		
		for (int i = 0; i < N; i++) {
		    for (int j = 0; j < M; j++) {
		        dp[i][j] = (dp[i][j] + 1) % MOD_NUM;
		        
		        char curC = map.get(i).charAt(j);
		        
		        if (curC == 'X') {
		            res = dp[i][j];
		            break;
		        }
		        
		        move(i, j, curC);
		    }
		}
		
		System.out.print(res);
	}
	
	static void move(int row, int col, char mark) {
	    if (mark == 'E' || mark == 'B') {
	        dp[row][col + 1] = (dp[row][col + 1] + dp[row][col]) % MOD_NUM;
	    }
	    if (mark == 'S' || mark == 'B') {
	        dp[row + 1][col] = (dp[row + 1][col] + dp[row][col]) % MOD_NUM;
	    }
	}
	
	static class Pair {
	    int row;
	    int col;
	    
	    Pair(int row, int col) {
	        this.row = row;
	        this.col = col;
	    }
	}
}

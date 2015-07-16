package dynamicprogramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * solves the coin change problem with dynamic programming.
 * 
 * each recursive call without DP does:
 * 1) recursion(value - largest coin);
 * ADDED WITH
 * 2) recursion(value, coins - largest coin);
 * 
 * with recursion, there's going to be a lot of calls with already calculated values that we have to calculate again.
 * thus, we store it in a table.
 * 
 * For example, we might do:
 * recursion(2, {1,2});
 * a lot of times so it's best to use DP.
 * 
 * Base Cases:
 * first row: using just one coin, there's only one way to make change out of any value
 * NOTE: the amount of change with this one coin must be equal exactly to value, or else it's impossible
 * and value returned should be 0.
 * first column: value == 0, there's only way to make change.
 * 
 * Cell Definition:
 * dp[row][column] = dp[value - largest coin] + dp[value, coins - largest coin];
 * note: out of bounds = 0;
 * 
 * Barney-Style Explanation:
 * we init the first row and first column with the value of one.
 * we fill the table one row at a time from left to right using the cell definition.
 * 
 * the columns = value
 * the rows = coins denominations.
 * 
 * For example, {1, 2, 3}, value = 5;
 * 
 *   0 1 2 3 4 5 <-- values
 * 1 b b b b b b
 * 2 b 1 2 3 4 5
 * 3 b 6 7 8 9 a
 * ^
 * ^- coin denominations.
 * first row says "ways we can make value with just {1}"
 * second row says "ways we can make value with just {1, 2}"
 * third row says "ways we can make value with just {1, 2, 3}"
 * 
 * the table is filled "b" (base cases) first.
 * then the rest of the cells are filled like: 1, 2, 3, 4, 5, 6, 7, 8, 9 a.
 * 
 * this class is just like CoinChangeProblem.java but it stores the values.
 * 
 * we filled the table cell's one by one so this algorithm is O(row * col).
 * 
 * @author L. Keh <LK00100100@gmail.com>
 *
 */
public class CoinChangeProblemDP {
	
	public static long[][] dp;

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int value 		= in.nextInt(); //variable N
		int numCoins 	= in.nextInt(); //variable M
		
		ArrayList<Integer> coins = new ArrayList<Integer>(numCoins);
		dp = new long[numCoins][value + 1];
		//last row = largest coin
		//[0 to value] inclusive.
		
		//get coin denominations
		for(int i = 0; i < numCoins; i++)
			coins.add(in.nextInt());
		
		//sort coins to ensure the largest coin is at the right end.
		Collections.sort(coins);
		
		//init DP table's base cases
		//first row. Using only just 1 coin there's only one make to make change at all times.
		//if the change is impossible, just return 0
		int smallestCoin = coins.get(0);
		for(int col = 0; col < dp[0].length; col++){
			if(col % smallestCoin == 0)
				dp[0][col] = 1;
			else
				dp[0][col] = 0;
		}
			
		//first col. since the first column is "value == 0", there is only way to make change for that
		//we have exact change.
		for(int row = 0; row < dp.length; row++)
			dp[row][0] = 1;
			
		//"unfilled"
		for(int row = 1; row < dp.length; row++){
			for(int col = 1; col < dp[0].length; col++){
				dp[row][col] = -1;
			}
		}
		
		//fill out the DP table, cell by cell, row by row (top to bottom, left to right).
		long valueMinusLargestCoin;
		long withoutLargestCoin;
		for(int row = 1; row < dp.length; row++){
			for(int col = 1; col < dp[0].length; col++){
				
				valueMinusLargestCoin = 0;
				//not negative value (out of bounds)
				if(col - coins.get(row) >= 0)
					valueMinusLargestCoin = dp[row][col - coins.get(row)];
				
				withoutLargestCoin = dp[row - 1][col];
				
				dp[row][col] = valueMinusLargestCoin + withoutLargestCoin;
			}
		}
		
		//print table
		for(int row = 0; row < dp.length; row++){
			for(int col = 0; col < dp[0].length; col++){
				System.out.print(dp[row][col]);
			}
			System.out.println();
		}
		
		//the lower right corner has our answer
		System.out.println(dp[dp.length - 1][dp[0].length - 1]);
		
		in.close();

	}

}

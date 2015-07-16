package dynamicprogramming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * solves the coin change problem without dynamic programming.
 * 
 * unlike regular brute force recursion, doing the recursion in this manner ensures unique permutations
 * 
 * with plain old recursion, we get the permutations:
 * 2, 1, 1
 * 1, 2, 1
 * 1, 1, 2
 * 
 * which are all the same. So by doing it in the manner stated below, we will only generate just
 * 2, 1, 1 instead of those unwanted extras. This is possible because we use the largest coin first.
 * 
 * For example, {1, 2, 3} and value of 5.
 * We add up all the ways we can make change using {1, 2, 3} with one 3, two 3s, three 3s, etc.
 * Then we add up all the ways we can make change using {1, 2} with one 2, two 2s, three 2s, etc.
 * Then We add up all the ways we can make change using just the 1's.
 * 
 * So for this particular problem, you'll generate something in this order: 
 * 3, 2
 * 3, 1, 1
 * 2, 2, 1
 * 2, 1, 1, 1
 * 1, 1, 1, 1, 1, 
 * 
 * Each permutation has a unique number of coins.
 * 
 * So to generate this "unique permutation tree", you add up:
 * recursion(ways you can make change after you use up the largest coin you have);
 * ADDED WITH
 * recursion(ways you can make change after you take out the largest coin);
 * 
 * This will ensure unique permutations (combinations-ish).
 * 
 * This becomes more clear if you draw out the tree diagram.
 * 
 * @author L. Keh <LK00100100@gmail.com>
 *
 */
public class CoinChangeProblem {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		int value 		= in.nextInt(); //variable N
		int numCoins 	= in.nextInt(); //variable M

		ArrayList<Integer> coins = new ArrayList<Integer>(numCoins);

		//get coin denominations
		for(int i = 0; i < numCoins; i++)
			coins.add(in.nextInt());

		//sort coins to ensure the largest coin is at the right end for recursion.
		Collections.sort(coins);

		System.out.println(coinChange(value, coins));

		in.close();

	}

	private static int coinChange(int value, ArrayList<Integer> coins) {
		//we gave too many coins
		if(value < 0)
			return 0;

		//just the right amount of change with the given coins
		if(value == 0)
			return 1;

		//no more coins left and obviously not the right amount of change.
		if(coins.isEmpty())
			return 0;

		//remove the largest value once and do recursion
		int largestCoin = coins.get(coins.size() - 1);	//you don't need more variables but it makes the code clearer.
		int a = coinChange(value - largestCoin, coins);

		//if possible, remove the largest coin and do recursion.
		if(coins.size() > 1){//if we are left with no more coins after removing, it is pointless.
			int removedLargestCoin = coins.remove(coins.size() - 1);	//take the largest coin out.
			int b = coinChange(value, coins);
			coins.add(removedLargestCoin);	//put the largest coin back in.
			return a + b;
		}
		//else
		return a;

	}

}

package edu.algo.ps3;

import java.util.Arrays;

public class Q4 {
	public static Double makeChange(int[] d, int n) {
		Double solution[] = new Double[n+1];
		
		// Sort the denominations
		Arrays.sort(d);		// ## Complexity = m*log(m)
		
		// Set no solution
		solution[0] = 0.0; // Set solution for zero vale to zero;
		for (int i=1; i<n+1; i++) 
			solution[i] = Double.POSITIVE_INFINITY;
		
		// Solve for every amount
		for (int i=1; i<n+1; i++){// ## Complexity = (n+1)*m = n*m
			for (int j=d.length-1;j>=0;j--) {
				// check if the coin value is less than the amount needed
				if (d[j] <= i) {
					solution[i] = solution[i - d[j]] + 1;
					// As array is sorted this should be the optimal solution
					if(solution[i] != Double.POSITIVE_INFINITY)
						break;
				}
			}
			System.out.println(i+"\t"+Arrays.asList(Arrays.copyOf(solution,i)));
		}
		return solution[n];
	}

	
	public static void main(String[] args) {
		int n = 15;
		int[] d = {1, 7, 8};
		System.out.println(makeChange(d, n));
	}

}
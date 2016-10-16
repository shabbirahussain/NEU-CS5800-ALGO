package edu.algo.ps3;

import java.util.Arrays;

public class Q2 {
	public static Double optimalBuy(Double A[][] , int n) {
		Double solution[][] = new Double[A.length][n+1];
		
		for(int i=0;i<n+1;i++)	// Assign a default solution when there is only 1 supplier
			solution[0][i] = A[0][i];
		
		// Now iteratively search for solution 
		for (int i=1; i<A.length; i++){// ## Complexity = m*(n+1)
			for (int j=0; j<n+1; j++){
				solution[i][j] = Math.min(A[i][j] + solution[i-1][n-j],
						solution[i-1][j]);
			}
			System.out.println(i+"\t"+Arrays.asList(solution[i]));
		}
		
		double min = Double.MAX_VALUE;
		for(int j=0; j<n+1; j++)
			min = Math.min(min, solution[A.length-1][j]);
		return min;
	}

	
	public static void main(String[] args) {
		int n = 2;
		Double[][] A = {{0.0, 1.0, 10.0},{0.0, 2.0, 8.0}};
		System.out.println(optimalBuy(A, n));
	}

}
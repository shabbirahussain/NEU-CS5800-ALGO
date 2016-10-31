package edu.algo.ps3;

public final class Q4_1 {
	public static void main(String[] args) {
		int n, i, res=-1;
		int d[] = {3,5,10};
		n = 19;
		for(i=d.length-1;i>=0;i--){
			res = fitCoins(d, 0, i, n);
			if(res>0) break;
		}
		System.out.println(res);
	}
	
	/**
	 * @param d is sorted array of denomination 
	 * @param p is the start index of array (Starting at 0)
	 * @param q is the end index of array
	 * @param n is the number to be built
	 * @return Total number of coins needed to fill n
	 */
	private static int fitCoins(int[] d, int p, int q, int n){
		int currDen, currCnt, innrCnt, newN;
		if (n==0) 			return 0;		// Task complete
		
		currDen = binaryFind(d, p, q, n);	// Find a denomination that is just <= n
		if(currDen == -1) 	return -1;		// Can't fit the requested number
		
		currCnt = n/d[currDen];				// No of coins needed of current denomination
		newN 	= n%d[currDen];				// Reduce n to remaining value
		innrCnt = fitCoins(d, p, currDen-1, newN); // Fit lower denominations
		if(innrCnt == -1){					// Can't fit any lower denominations
			return fitCoins(d, p, currDen-1, n);
		}
		return currCnt + innrCnt;
	}
	
	/**
	 * Performs binary search to find an element that is just less than or equal to given n
	 * @param d is sorted array of denomination 
	 * @param p is the start index of array (Starting at 0)
	 * @param q is the end index of array
	 * @param n is the number to be built
	 * @return Position of a value less than or equal to n. If none found return -1. 
	 */
	private static int binaryFind(int[] d, int p, int q, int n){
		int len, mid;
		len = q-p+1;
		
		if(len==0) 				return -1;
		mid = len/2;
		if(d[mid] == n){ 		return mid;
		}else if(d[mid]<n){
			if(mid+1 == len) 	return mid;
			if(d[mid+1]>n)		return mid;
			
			return (mid+1)+binaryFind(d, mid+1, q, n);
		}else{
			return binaryFind(d, p, mid-1, n);
		}
		
	}
}

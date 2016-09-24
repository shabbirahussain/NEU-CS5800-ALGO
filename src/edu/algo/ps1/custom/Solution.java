package edu.algo.ps1.custom;

import java.util.*;
import java.math.BigInteger;

public class Solution {
    private static final BigInteger b1 = new BigInteger("1");
    private static final BigInteger b3 = new BigInteger("3");
    private static final BigInteger b7 = new BigInteger("7");
    
    /** 
     * Finds the nth Holy Number for the given input n. 
     * Holy Number: We say a positive integer is holy if its prime factors only include 3 and 7. As some of you may know, the numbers 3 and 7 are both considered "perfect numbers" under the Hebrew tradition and hence we consider these two and all of their composites to be holy.
     * @param n is the index of Holy Number to find.
     * @return Value of the nth Holy Number.
     */ 
    public static BigInteger getNthHolyNumber(int n){
        PriorityQueue<BigInteger> open  = new PriorityQueue<BigInteger>();
        //Set<BigInteger> closed = new HashSet<BigInteger>();
        open.add(b1);
        
        int closeCnt = -1; // compensate for values less than 3
        BigInteger result=BigInteger.ZERO, r3, r7;
        while(closeCnt!=n){
            closeCnt ++;
            result = open.poll();

            //System.out.println(open);
            r3 = result.multiply(b3);
            r7 = result.multiply(b7);
            if(!open.contains(r3))
                open.add(r3);
            open.add(r7);
        }
        //System.out.println(open.size());
        return result;
    }

    public static void main(String[] args) {
    	long s = System.currentTimeMillis();
    	
    	System.out.println(getNthHolyNumber(777777));
    	
    	System.out.println("Time taken="+((System.currentTimeMillis()-s)));
    	
    }
}
package edu.algo.ps1.given;

import java.math.BigInteger;

public class Solution {
    private static final BigInteger b1 = new BigInteger("1");
    private static final BigInteger b3 = new BigInteger("3");
    private static final BigInteger b7 = new BigInteger("7");
    
    private static BigInteger getNthHolyNumber(int n){
        int i = 1;
        BigInteger cur = b3;
        
        while(i!=n+1){
            if(isHoly(cur))
                i++;
            cur = cur.add(BigInteger.ONE);
        }
        System.out.println("i="+i+"\tcurr="+cur);
        return cur.subtract(BigInteger.ONE);
        
    }
    
    private static boolean isHoly(BigInteger n){
        if(n.compareTo(b3) < 0 ) return false;
        while (n.mod(b7).equals(BigInteger.ZERO)) n = n.divide(b7);
        while (n.mod(b3).equals(BigInteger.ZERO)) n = n.divide(b3);
        
        return n.equals(BigInteger.ONE);
    }
    
    public static void main(String[] args) {
    	long s = System.currentTimeMillis();
    	//BigInteger test = new BigInteger("3476071353656320717348627297381957741394944297622451053318787109808717471129049");
    	
    	System.out.println(getNthHolyNumber(10));
    	
    	System.out.println("Time taken="+((System.currentTimeMillis()-s)*1e-3));
    	
    	
//        Scanner in = new Scanner(System.in);
//        while(in.hasNextInt())
//            System.out.println(getNthHolyNumber(in.nextInt()));
    }
}
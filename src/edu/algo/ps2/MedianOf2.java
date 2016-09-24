package edu.algo.ps2;

import java.util.Arrays;
import java.util.Random;

public class MedianOf2 {

	public static double getMedian(Double a[], int p, int q){
		int len = (q-p)+1;
		int mid = len/2;

		if(len%2==0) return (a[p+mid-1]+a[p+mid-2])/2.0;
		else         return a[p+mid-1];
	}

	public static double getMedian(Double a[], int pa, int qa, Double b[], int pb, int qb){
		double midA = getMedian(a, pa, qa);
		double midB = getMedian(b, pb, qb);
		
		
		int len = (qa-pa)+1;
		if(len<=2){	
//			System.out.println(len+"\t"+pa+"\t"+pb);
			Double c[]= new Double[len*2];
			System.arraycopy(a, pa-1, c, 0, len);
			System.arraycopy(b, pb-1, c, len, len);
			Arrays.sort(c);	
			
//			System.out.println(Arrays.asList(c));
			return getMedian(c, 1, c.length);
		}
		
		int mid = len / 2;
		int evenAdjust = (len%2==1)?0:-1;
		if(midA < midB)
			return getMedian(a, pa+mid+evenAdjust, qa, b, pb, pb+mid);
		else
			return getMedian(a, pa, pa+mid, b, pb+mid+evenAdjust, qb);
	}
	
	
	public static void main(String[] args) {
		Random rnd = new Random();
		
		Double[] a, b, c;
		for(int i=2;i<=20000000;i+=2){
			int len = rnd.nextInt(100);
			len = (len==0)?1:len;
			
			a = new Double[len];
			b = new Double[len];
			c = new Double[2*len];
			for(int j=0;j<len;j++){
				a[j]=rnd.nextDouble();
				b[j]=rnd.nextDouble();
			}
			System.arraycopy(a, 0, c, 0, a.length);
			System.arraycopy(b, 0, c, a.length, b.length);
			Arrays.sort(a);
			Arrays.sort(b);
			Arrays.sort(c);
			
			Double m1 = getMedian(c, 1, c.length);
			Double m2 = getMedian(b, 1, a.length, a, 1, b.length);
			
			if(!m1.equals(m2)){
				System.out.println("\nA="+Arrays.asList(Arrays.copyOfRange(a, 0, len)));
				System.out.println("B="+Arrays.asList(Arrays.copyOfRange(b, 0, len)));

				System.out.println(m1+"\t"+m2);
			}
		}
	}
	
}

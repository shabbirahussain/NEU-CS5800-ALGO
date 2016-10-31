package edu.algo.ps4;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Q4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new Q4()).getInfected(null);
	}
	
	class Tuple implements Comparable<Tuple>{
		public String c0, c1;
		public int time;
		public Tuple(String c0, String c1, Integer time){
			this.c0 	= c0;
			this.c1 	= c1;
			this.time 	= time;
		}

		@Override
		public int compareTo(Tuple o) {
			return this.time - o.time;
		}
		
	}
	class ITuple{
		public String name;		// Computer name
		public int time;		// Infection Time
		public List<String> p;	// Infection Path
		public ITuple(String name){
			this.p 		= new LinkedList<String>();
			this.name 	= name;
			this.time 	= 0;
		}
		public ITuple(String name, Integer time, ITuple o){
			this.name = name;
			this.p = new LinkedList<>(o.p);
			this.p.add(o.name);
			this.time = time;
		}
	}
	
	private Collection<ITuple> getInfected(Tuple TA[]){
		Map<String, ITuple> iMap = new HashMap<>();
		
		Arrays.sort(TA); 						// Merge sort array
		iMap.put("C0", new ITuple("C0"));		// Add patient zero
		
		ITuple c0, c1, src;
		String tgt;
		for(Tuple T:TA){						// O(t)
			c0 = iMap.get(T.c0);				// O(log n)
			c1 = iMap.get(T.c1);				// O(log n)
			
			if(c0 != null && c1 != null)	continue; // Both are infected skip
			if(c0 == null && c1 == null)	continue; // Both are uninfected skip
			if(c0 != null){src = c0; tgt = T.c1;}
			else		  {src = c1; tgt = T.c0;}
			
			iMap.put(tgt, new ITuple(tgt, T.time, src));
		}
		return iMap.values();
	}

}

package edu.algo.misc.clique;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static edu.algo.misc.clique.Constants.*;

public class Executor {	
	public static void main(String[] args) throws FileNotFoundException {
		// Initialize
		CliqueFinder e = new CliqueFinder(MTX_FILE);
		String r = e.gFull.vertexSet().iterator().next();
		
		// Execute clique search
		e.findClique(r, new HashSet<String>());
		
		// Print results
		System.out.print("\n\n"+e.optSol.size()+"\t");
		printSet(e.optSol);
	}
	
	/**
	 * Prints the solution found
	 * @param optSol is the optimal solution to print
	 */
	public static void printSet(Set<String> optSol){
		List<Integer> sol = new ArrayList<Integer>(optSol.size());
		for(String s: optSol){
			sol.add(Integer.parseInt(s));
		}
		Collections.sort(sol);
		System.out.println(sol);
	}
}

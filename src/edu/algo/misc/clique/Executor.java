package edu.algo.misc.clique;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static edu.algo.misc.clique.Constants.*;

public class Executor {	
	public static void main(String[] args) throws FileNotFoundException {
		run(MTX_FILE);
	}
	
	public static int run(String mtxFileName) throws FileNotFoundException{
		// Initialize
		CliqueFinder e = new CliqueFinder(mtxFileName);
		
		// Execute clique search
		int msc = e.findClique();
		
		// Print results
		System.out.println("\n\nMy Solution:");
		System.out.println("Max clique Size : " + msc); 
		System.out.print("Maximum clique: "); printSet(e.optSol);
		System.out.println("Time taken : " + e.timeTaken + " SEC");
		
		System.out.print("\n\nSA Solution:");
		int sac = ResultValidator.runValidation(mtxFileName);
		
		System.out.print("\n\nDiff:"); System.out.println(sac - msc);
		return (sac - msc);
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

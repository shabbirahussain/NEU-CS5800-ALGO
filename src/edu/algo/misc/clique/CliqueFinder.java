package edu.algo.misc.clique;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;

public class CliqueFinder {
	public  static final Boolean UNDIRECTED = true;
	
	DirectedGraph<String, DefaultEdge> gFull, gUnvisited;
	Map<String, Set<String>> solMap;
	
	// Public results
	public Set<String> optSol;
	public Integer cnt;

	
	/**
	 * Default constructor
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CliqueFinder(String mtxPath) throws FileNotFoundException{
		this.gFull      = DataLoader.getGraph(mtxPath, UNDIRECTED);
		this.gUnvisited = (DirectedGraph<String, DefaultEdge>) ((AbstractBaseGraph) gFull).clone();
		
		optSol = new HashSet<>();
		solMap = new HashMap<>();
		cnt = 0;
	}
	
	/**
	 * Gets the children of the forest  
	 * @param node is the node whose children has to calculated
	 * @param onlyUnVisited filters out only nodes that are unvisited
	 * @return Set of child node names
	 */
	private Set<String> getChildren(String node, Boolean onlyUnVisited){
		Set<String> result = new HashSet<>();
		
		DirectedGraph<String, DefaultEdge> g;
		g = (onlyUnVisited)? gUnvisited: gFull;
		
		for(DefaultEdge e: g.edgesOf(node)){
			result.add(g.getEdgeTarget(e));
			if(UNDIRECTED) result.add(g.getEdgeSource(e));
		};
		
		result.remove(node);
		return result;
	}
	
	/**
	 * Sets a link to be visited
	 * @param u is the source of the link
	 * @param v is the target of the link
	 */
	private void markVisited(String u, String v){
		gUnvisited.removeEdge(u, v);
		if(UNDIRECTED) 
			gUnvisited.removeEdge(v, u);
	}
	
	
	/**
	 * Solves the clique problem
	 * @param r is the start node for recursion
	 * @param parents is the parent set of clique found till now
	 */
	public void findClique(String r, Collection<String> parents){
		// Build current solution
		Set<String> curr = new HashSet<>(parents);
		// Get children to expand
		Set<String> children = getChildren(r, false);
		// parents intersection r.children
		curr.retainAll(children);					// O(n*log(n))		
		// add current node to the frontier
		curr.add(r);								// O(log(n))
		
		// Get intermediate solution
		Set<String> prev = solMap.get(r);			// O(log(n))
		// Merge both solutions
		if(prev!=null){
			Set<String> common = new HashSet<>(curr);
			common.retainAll(prev);					// O(n*log(n))
			
			curr.removeAll(common);					// O(n*log(n))
			prev.removeAll(common);					// O(n*log(n))
			
			Set<String> big, sml;
			if(curr.size() > prev.size()){ big = curr; sml = prev; }
			else                         { big = prev; sml = curr; }

			for(String c:sml){						// O(n^2 log(n))
				cnt++;
				Set<String> cc = getChildren(c, false);
				cc.retainAll(big);					// O(n*log(n))
				if(cc.size() == big.size())
					common.add(c);					// O(n*log(n))
			}
			common.addAll(big);						// O(n*log(n))
			curr = common;
		}
		
		// Put solution back to storage
		solMap.put(r, curr);						// O(log(n))
		
		// Store global optimal
		if(optSol.size()<curr.size()) optSol = curr;
		System.out.print("\tcurr="); Executor.printSet(curr);
		
		do{
			// Look for only unvisted children
			children = getChildren(r, true);
			if(!children.iterator().hasNext()) break;
			
			String c = children.iterator().next();
			markVisited(r, c); 
			
			System.out.print((++cnt)+"\tNode: "+r+"->"+c);
			
			// Recurse
			findClique(c, curr);
		}while(true);
	}
	
	
}

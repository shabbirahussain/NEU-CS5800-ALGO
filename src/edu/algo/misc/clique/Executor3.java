package edu.algo.misc.clique;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.graph.*;

public class Executor3 {
	UndirectedGraph<String, DefaultEdge> graph;
	Set<String> visited;
	Set<String> solution;
	
	public Executor3(){
		this.graph = createStringGraph();
		visited  = new HashSet<>();
		solution = new HashSet<>();
	}
	
	public void findClique(String r, Collection<String> parents){
		System.out.print("\nNode="+r);
		Set<String> curr = new HashSet<>(parents);
		Set<String> children = getChildren(r);
		System.out.print("\t"+children);
		
		curr.retainAll(children);					// parents intersection r.children
		curr.add(r);								// add current node to the frontier
		if(solution.size()<curr.size())
			solution = curr;
		System.out.print("\t="+curr);
		
		visited.add(r);
		for(String c: children){
			if(visited.contains(c)) continue;
			findClique(c, curr);
		}
	}
	
	public static void main(String[] args) {
		// Initialize
		Executor3 e = new Executor3();
		Set<String> parents = new HashSet<>();
		String r = e.graph.vertexSet().iterator().next();
		
		// Execute clique search
		e.findClique(r, parents);
		
		// Print results
		System.out.println("\n\n"+e.solution);
	}
	
	private Set<String> getChildren(String r){
		Set<String> result = new HashSet<>();
		for(DefaultEdge e: graph.edgesOf(r)){
			result.add(graph.getEdgeTarget(e));
			result.add(graph.getEdgeSource(e));
		};
		result.remove(r);
		return result;
	}
	
	/**
     * Create a toy graph based on String objects.
     *
     * @return a graph based on String objects.
     */
    private static UndirectedGraph<String, DefaultEdge> createStringGraph()
    {
        UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        String v5 = "v5";
        String v6 = "v6";
        String v7 = "v7";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v1, v3);
        g.addEdge(v1, v4);
        g.addEdge(v1, v6);
        
        g.addEdge(v2, v3);
        g.addEdge(v2, v5);
        g.addEdge(v2, v7);

        g.addEdge(v3, v4);
        g.addEdge(v3, v5);
        
        g.addEdge(v4, v5);
        g.addEdge(v4, v6);
        g.addEdge(v4, v7);
        
        g.addEdge(v5, v6);
        g.addEdge(v5, v7);

        g.addEdge(v6, v7);

        return g;
    }

}

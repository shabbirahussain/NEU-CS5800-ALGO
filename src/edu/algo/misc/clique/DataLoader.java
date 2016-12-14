package edu.algo.misc.clique;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DataLoader {
	/**
	 * Reads and parses a mtx file to build a graph
	 * @param filePath is the full file path to read MTX from
	 * @param undirected specifies if bidirectional edges to be added by default
	 * @return DirectedGraph
	 * @throws FileNotFoundException
	 */
	public static Graph<String, DefaultEdge> getGraph(String filePath, Boolean undirected) throws FileNotFoundException{
    	Graph<String, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
    	
    	Scanner in = new Scanner(new FileInputStream(filePath));
    	while(in.hasNextLine()){
    		String line = in.nextLine();
    		if(line.startsWith("%")) continue; 	//Skip comments
    		
    		String tokens[] = line.split(" ");
    		if(tokens.length == 2){				// Add edges
    			if(! g.containsVertex(tokens[0])) g.addVertex(tokens[0]);
    			if(! g.containsVertex(tokens[1])) g.addVertex(tokens[1]);
    			
    			g.addEdge(tokens[0], tokens[1]);
    			//if(undirected)
    				g.addEdge(tokens[1], tokens[0]);
    		}
    	}
    	in.close();
    	System.out.println(g.vertexSet());
    	return g;
	}
 
}

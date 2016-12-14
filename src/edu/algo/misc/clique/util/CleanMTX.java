package edu.algo.misc.clique.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import edu.algo.misc.clique.Executor;

import static edu.algo.misc.clique.Constants.*;
public class CleanMTX {
	Graph<String, DefaultEdge> g;
	String header;
	
	public CleanMTX() throws FileNotFoundException{
		header = "";
		readMTX(MTX_FILE);
	}
	

	public static void main(String[] args) throws IOException {
		CleanMTX c = new CleanMTX();
		c.permuteMTX();
		
		//c.writeMTX(MTX_FILE);
	}
	
	private void permuteMTX() throws IOException{
		String tmpFilePath     = MTX_FILE + ".tmp.mtx";
		
		Set<DefaultEdge> edges = new HashSet<>(g.edgeSet());
		Set<DefaultEdge> eSave = new HashSet<>(edges);
		
		Path sPath = Paths.get(tmpFilePath);
		Path tPath = Paths.get(MTX_FILE);
		
		for(DefaultEdge e: edges){
			Set<DefaultEdge> eNew = new HashSet<>(eSave);
			
			// Remove an edge
			eNew.remove(e);
			
			// Write back mtx
	    	writeMTX(tmpFilePath, eNew);
			
	    	// Compare results
	    	if(Executor.run(tmpFilePath)>0){
	    		eSave = new HashSet<>(eNew);

				Files.move(sPath, tPath, StandardCopyOption.REPLACE_EXISTING);
	    	}
		}
		
		
		if(eSave.size()<edges.size()){
			//writeMTX(MTX_FILE, eSave);
		}
		
		if (Files.exists(sPath))
			Files.delete(sPath);
	}
	
	private void writeMTX(String path) throws FileNotFoundException{
		writeMTX(path, g.edgeSet());
	}
	
	private void writeMTX(String path, Set<DefaultEdge> edges) throws FileNotFoundException{
		PrintWriter out = new PrintWriter(new FileOutputStream(path));
    	out.write(header);
    	for(DefaultEdge e: edges){
    		out.write(g.getEdgeSource(e) + " " + g.getEdgeTarget(e) + "\n");
    	}
    	out.close();
	}
	
	private void readMTX(String path) throws FileNotFoundException{
		this.g = new DefaultDirectedGraph<>(DefaultEdge.class);
		
		Scanner in = new Scanner(new FileInputStream(path));
		int c=0;
    	while(in.hasNextLine()){
    		String line = in.nextLine();
    		if((c++)<2) header += line +"\n";
    		
    		if(line.startsWith("%")) continue; 	//Skip comments
    		
    		String tokens[] = line.split(" ");
    		if(tokens.length == 2){				// Add edges
    			if(! g.containsVertex(tokens[0])) g.addVertex(tokens[0]);
    			if(! g.containsVertex(tokens[1])) g.addVertex(tokens[1]);
    			
    			g.addEdge(tokens[0], tokens[1]);
    		}
    	}
    	in.close();
	}

}

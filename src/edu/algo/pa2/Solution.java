package edu.algo.pa2;

import java.io.*;
import java.util.*;

/**
 * Solves the given problem using a graph search approach.
 * It recursively searches each path till a solution is found or fuel is depleted. And chooses the best path at every node.
 * 
 * Complexity: O(V+E) // Graph traversal complexity
 * 
 * @author shabbirhussain
 */


public class Solution {
    private static Map<String, Node> graph;                 // DAG
    private static int fuel;
    private static String start, end;
    
    public static void main(String[] args) {
        // Read and parse inputs
    	readInputs();
        
        // Initialize search
        State s0 = new State(start, 0, fuel);
        
        // Execute search
        System.out.println(solve(s0, end));
    }
    
    private static void readInputs(){
    	Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        fuel  = in.nextInt();
        
        // Scan and parse input to build graph
        graph = new HashMap<>(n);
        for(int i=0;i<n;i++){
        	String u = in.next();
        	graph.put(u, new Node(u));
            
            if(i==0)     start = u;
            if(i==(n-1)) end   = u;
        }
        
        in.nextLine();
        while(in.hasNextLine()){
            String[] s = in.nextLine().split(" ");
            
            Node node  = graph.get(s[0]);
            node.addEdge(s[1], (s.length==3));
            graph.put(s[0],  node);
        }
        in.close();
    }
    
    /**
     * Solves the graph search iteratively(DFS) given the starting state and the goal
     * @param s0 is the starting state
     * @param end is the goal label
     * @return Resultant state after search. If successful then a linked list of states is returned from start to the goal node. If unsuccessful then longest path traversed is returned ending at label "null". 
     */
    private static State solve(State s0, String end){
        // Found solution
        if(s0.name.equals(end)){
        	s0.fuelR = s0.fuel;
        	return s0;
        }
        
        // Calculate solution for each path and pick best
    	List<State> children = s0.getSuccessors(graph);
    	
    	//System.out.println(s0.name+":"+children);
    	State bestSol = new State(null, 0, 0);
    	for(State s1: children){
    		State sol = solve(s1, end);			// Recursively solve new state
    		if(bestSol.compareTo(sol)<0){		// Better solution is found 
    			bestSol = sol;
    		}
    	}
    	s0.next  = bestSol;
        s0.fuelR = bestSol.fuelR;
        
        return s0;
    }
}

///////////////////////////////////////////////////////////////////////////////////
//        Storage state of the search
///////////////////////////////////////////////////////////////////////////////////

/**
 * Stores the state of the search
 * @author shabbirhussain
 */
class State implements Comparable<State>{
    public String name;		// Name of the vertex
    public int    time;		// Time 
    public double fuel;		// Depicts the initial fuel of the state
    public double fuelR;	// Depicts the remaining fuel once it reached the end
    public State  next;		// Next step pointer
    
    public State(String name, int time, double fuel){
        this.name  = name;
        this.fuel  = fuel;
        this.time  = time; 
        
        this.fuelR = Double.NEGATIVE_INFINITY;
        this.next  = null;
    }
    
    /**
     * Generates successors of a given state
     * @param graph is the graph to search for
     * @param isEven is flag representing current time step is even or not
     * @return List of successors
     */
    public List<State> getSuccessors(Map<String, Node> graph){
    	List<State> result = new LinkedList<>();
    	Node n0 = graph.get(name);
    	
    	Boolean isEven = (this.time%2==0);
        for(Edge e: n0.edges){
    		if(!e.canTraverse(this.fuel)) continue;
    		if(isEven && e.isWHole)       continue;
    		
    		double fuelNew = e.fuelLeftPostTraversal(fuel);
    		int    timeNew = e.timePostTraversal(time);
    		
    		State s1 = new State(e.v ,timeNew, fuelNew);
    		result.add(s1);
    	}
    	return result;
    }
    
    
	@Override
	public int compareTo(State o) {
		return (int) (this.fuelR - o.fuelR);
	}
	@Override
	public String toString(){
		return this.name + ((this.next != null)?(" " + this.next.toString()):"");
	}
}

///////////////////////////////////////////////////////////////////////////////////
//		Storage of graph
///////////////////////////////////////////////////////////////////////////////////

/**
 * Generic class to store the edge of the graph
 * @author shabbirhussain
 */
abstract class Edge{
	public String v;			// Target of the edge
	public boolean isWHole;		// Specifies if edge is a wormhole edge
	
	public Edge(String v, boolean wh){
		this.v 		 = v;
		this.isWHole = wh;
	}
	/**
	 * Identifies if that edge can be traversed with fuel given
	 * @param fuel is the fuel to start with
	 * @return True iff fuel is enough to traverse the edge
	 */
	abstract boolean canTraverse(double fuel);
	/**
	 * Calculates the amount of fuel left after edge traversal
	 * @param fuel is the fuel to start with
	 * @return Fuel left after traversal
	 */
	abstract double fuelLeftPostTraversal(double fuel);
	/**
	 * Calculates the time after traversal given start time
	 * @param time is the time to start with
	 * @return Time after edge traversal
	 */
	abstract int timePostTraversal(int time);
}

/**
 * A regular edge 
 * @author shabbirhussain
 */
class NEdge extends Edge{
	public NEdge(String v){
		super(v, false);
	}
	@Override
	public boolean canTraverse(double fuel){
		return (fuel>=1);
	}
	@Override
	public double fuelLeftPostTraversal(double fuel) {
		return fuel-1;
	}
	@Override
	public int timePostTraversal(int time) {
		return time+1;
	}
}

/**
 * Wormhole edge
 * @author shabbirhussain
 */
class WEdge extends Edge{
	public WEdge(String v){
		super(v, true);
	}
	@Override
	public boolean canTraverse(double fuel){
		return (fuel>0.0);
	}
	@Override
	public double fuelLeftPostTraversal(double fuel) {
		return fuel/2.0;
	}
	@Override
	public int timePostTraversal(int time) {
		return time;
	}
}

/**
 * Stores a node of the graph
 * @author shabbirhussain
 */
class Node{
	public String u;
	public List<Edge> edges;
	
	/**
	 * Default constructor
	 * @param u is the node name
	 */
    public Node(String u){
        this.u  = u;
        this.edges = new LinkedList<>();
    }
    
    /**
     * Adds an edge to the graph
     * @param v is the target of the edge
     * @param wh specifies if there is worm hole
     * @return current object
     */
    public Node addEdge(String v, Boolean wh){
    	Edge e;
    	if(wh) e = new WEdge(v);
    	else   e = new NEdge(v);
    	this.edges.add(e);
    	
    	return this;
    }
}
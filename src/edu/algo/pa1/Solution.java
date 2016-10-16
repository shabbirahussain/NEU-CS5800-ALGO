package edu.algo.pa1;

import java.io.*;
import java.util.*;


class Circle implements Comparable<Circle>{
    double x,y,r;
    public Circle(int x, int y, double r){
        this.x = x;this.y = y;this.r = r;
    }
    
	@Override
	public int compareTo(Circle o) {
		double cenDist = Math.hypot(this.x - o.x, this.y - o.y);
		if(cenDist <= Math.abs(this.r - o.r))
			if(this.r > o.r) 	return  1;
			else				return -1;
		else 					return  0;
	}
    
	@Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("[" + x + "," + y + "," + r + "]");
        return str.toString();
    }
}



class Heap<T extends Comparable<T>>{
	private class Node{
		T     data;
		List<Node> children;
		
		/**
		 * Default constructor
		 * @param data is the object to be stored in tree
		 */
		public Node(T data){
			this.data = data;
			this.children = new LinkedList<>();
		}
	    
		@Override
	    public String toString(){
	        StringBuilder str = new StringBuilder();

	        str.append("{"+data.toString()+":");
	        for(Node n: children)
	            str.append(n.toString());
	        str.append("}");
	        
	        return str.toString();
	    }
	}
	
	private Node root;
	
	/**
	 * Default constructor
	 * @param root is the root node to create heap about
	 */
	public Heap(T root){
		this.root = new Node(root);
	}
	
	/**
	 * Adds element to the heap
	 * @param o is the object to be added
	 * @return True is element is successfully added to heap. It fails if the root node is smaller than the given element.
	 */
	public boolean addToHeap(T o){
		return this.addToHeap(root, new Node(o));
	}
	
	/**
	 * Traverses the tree and finds the max depth of n-ary tree 
	 * @return Maximum depth of the child from the root 
	 */
	public int getMaxDepth(){
		return this.getMaxDepth(root);
	}
	
	/**
	 * Adds element to the heap given root node and element
	 * @param root is the root node of the heap
	 * @param o is the object of node to be added into heap
	 * @return True is element is successfully added to heap. It fails if the root node is smaller than the given element.
	 */
	private boolean addToHeap(Node root, Node o){
		if(root.data.compareTo(o.data)== 1){ // Circle is smaller than root
            // Try to add it to children
			ListIterator<Node> ci = root.children.listIterator();
			while(ci.hasNext()){
                Node n = ci.next();	                
                if(n.data.compareTo(o.data)==-1){	// Circle is greater than child
                	addToHeap(o, n);				// Move branch inside the circle
                	ci.set(o);						// Update current branch
                    return true;
                }else{								// Circle is less than child
				    boolean res = addToHeap(n, o);
				    if(res) return true;	       	// Successfully added to child
                }
			}
			// If circle cannot be added to child then create it's new branch 
			root.children.add(o);
			return true;
		}
		return false;
	}
	
	/**
	 * Traverses the tree and finds the max depth of n-ary tree 
	 * @param root is the root node of the tree
	 * @return Maximum depth of the child from the root 
	 */
	private int getMaxDepth(Node root){
		if(root==null) return 0;
		int max = 0;
		for(Node n:root.children){
			 max = Math.max(max, getMaxDepth(n));
		}
		return max + 1;
	}
}

public class Solution {
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        List<Circle> inputs = new LinkedList<>();
        for(int i=0;i<n;i++)
            inputs.add(new Circle(in.nextInt(), in.nextInt(), in.nextInt()));
        in.close();

        // Create a dummy root node
        Circle root       = new Circle(0, 0, Double.POSITIVE_INFINITY);
        Heap<Circle> heap = new Heap<>(root);
        
        for(Circle c: inputs)
        	heap.addToHeap(c);
        
        System.out.println(heap.getMaxDepth()-2);
    }
}
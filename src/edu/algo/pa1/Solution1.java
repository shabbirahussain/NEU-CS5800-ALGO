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

class Node{
	Circle     data;
	List<Node> children;
	
	/**
	 * Default constructor
	 * @param data is the object to be stored in tree
	 */
	public Node(Circle data){
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


public class Solution1 {
	private static boolean addToHeap(Node root, Node c){
		if(root.data.compareTo(c.data)== 1){ // Circle is smaller than root
            // Try to add it to children
			ListIterator<Node> ci = root.children.listIterator();
			while(ci.hasNext()){
                Node n = ci.next();	                
                if(n.data.compareTo(c.data)==-1){	// Circle is greater than child
                	addToHeap(c, n);				// Move branch inside the circle
                	ci.set(c);						// Update current branch
                    return true;
                }else{								// Circle is less than child
				    boolean res = addToHeap(n, c);
				    if(res) return true;	       	// Successfully added to child
                }
			}
			// If circle cannot be added to child then create it's new branch 
			root.children.add(c);
			return true;
		}
		return false;
	}
	
	/**
	 * Traverses the tree and finds the max depth of n-ary tree 
	 * @param root is the root node of the tree
	 * @return Maximum depth of the child from the root 
	 */
	private static int getMaxDepth(Node root){
		if(root==null) return 0;
		int max = 0;
		for(Node n:root.children){
			 max = Math.max(max, getMaxDepth(n));
		}
		return max + 1;
	}

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        List<Circle> inputs = new LinkedList<>();
        for(int i=0;i<n;i++)
            inputs.add(new Circle(in.nextInt(), in.nextInt(), in.nextInt()));
        in.close();

        // Create a dummy root node
        Node root = new Node(new Circle(0, 0, Double.POSITIVE_INFINITY));
        for(Circle c: inputs)
        	addToHeap(root, new Node(c));
        
        System.out.println(getMaxDepth(root)-2);
    }
}
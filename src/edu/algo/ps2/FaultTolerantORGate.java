package edu.algo.ps2;

public class FaultTolerantORGate {


	public static void main(String[] args){
		FaultTolerantORGate f = new FaultTolerantORGate();
	}
	
	boolean or(boolean a, boolean b){
		return a||b;
	}
	
	boolean and(boolean a, boolean b){
		return a&&b;
	}
	
	boolean faultyOr(boolean a, boolean b){
		return a&&b;
	}
	
	boolean faultyAnd(boolean a, boolean b){
		return a||b;
	}
}

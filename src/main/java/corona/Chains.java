package corona;

import java.util.ArrayList;



public class Chains {
	private  ArrayList<Chain> chains;
	private double lasttimestamp;
	
	public ArrayList<Chain> getArrayList() {
		return chains;
	}
	public Chain getChain(int i) {
		return chains.get(i);
	}
	
}

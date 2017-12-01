/* This class describes the evolution algorithm used to find a solution
 * 
 */

import java.util.Random;

import microgrid.Individual;

public class Evolution {
	
	
	// Fields
	Individual initial;
	
	// Constructor
	public Evolution(Individual initial) {
		System.out.println("Starting Evolution!");
		this.initial = initial;
		
		
		
	}
	/*
	// Run the algorithm
	public Individual run() {
		
		Random rnd = new Random();
		
		Individual current = initial;
		Individual next = initial;
		int n = current.getParameters().length;
		float[] delta = new float[n];
		float[] sigmas = current.getSigmas();
		
		int k = 1;
		while (cost > 10.0 && k < 100) {
			
			float next_cost;
			
			for (int i=0; i<n; i++) {
				delta[i] =  sigmas[i]*((float) rnd.nextGaussian());
			}
			next.applyDelta(delta);
			next_cost = next.cost();
			if (cost > next_cost) {
				current = next;
				cost = next_cost;
			} else {
				next = current;
			}
			k++;
			System.out.println("Cost: " + cost + "	Iteration: " + k);
			
		}
		return current;
	}
	*/
	
}

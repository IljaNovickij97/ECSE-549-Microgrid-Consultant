/* This class describes the individual design of a Microgrid in the evolution algorithm
 * 
 */

public class Individual {
	// Fields
	private int ID;
	private float parameters[];
	private float sigmas[];
	
	
	// Constructor
	public Individual(int ID, float parameters[], float sigmas[]) {
		setID(ID);
		setParameters(parameters);
		setSigmas(sigmas);
		
	}
	
	// Simple test cost function method
	public float cost() {
		float cost = 0;
		
		for (int i=0; i<parameters.length; i++) {
			cost += parameters[i];
		}
		
		
		return cost;
	}
	
	
	// Printing method
	public void print() {
		System.out.println("ID is " + ID);
		System.out.println("Parameters:");
		for (int i=0; i<parameters.length; i++) {
			System.out.print(parameters[i] + " ");
		}
		System.out.println(" ");
		System.out.println("Sigmas:");
		for (int i=0; i<sigmas.length; i++) {
			System.out.print(sigmas[i] + " ");
		}
		System.out.println(" ");
	}


	public float[] getParameters() {
		return parameters;
	}

	
	public void setParameters(float parameters[]) {
		this.parameters = parameters;
	}
	
	public void applyDelta(float delta[]) {
		for(int i=0; i<parameters.length; i++) {
			this.parameters[i] += delta[i];
		}
		
	}


	public float[] getSigmas() {
		return sigmas;
	}


	public void setSigmas(float sigmas[]) {
		this.sigmas = sigmas;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}


}


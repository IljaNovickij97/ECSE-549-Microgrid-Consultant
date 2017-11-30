

/* This is the main class of the program. The program runs from here.
 * 
 */

public class Initialization {
	
	public static void main(String[] args){
		
		float[] parameters = new float[]{ 1,2,3,4,5,6,7,8,9,10 }; 
		float[] sigmas = new float[]{ 1,1,1,1,1,1,1,1,1,10 }; 
		System.out.println("Hello There!");
		Individual test = new Individual(1,parameters, sigmas);
		test.print();
		Evolution evo = new Evolution(test);
		Individual result = evo.run();
		result.print();
		JessTest jesstest = new JessTest();
		jesstest.run();
	}
	
}

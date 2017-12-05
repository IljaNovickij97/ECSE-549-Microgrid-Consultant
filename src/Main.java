import microgrid.*;

/* This is the main class of the program. The program runs from here.
 * 
 */

public class Main {
	

	
	public static void main(String[] args){
		
		Evolution ev = new Evolution();
		Interface inter = new Interface();
		JessInterface jessinter = new JessInterface();
		Individual initial = inter.openFile();
		double[] sigmas = {0.0,0.0,0.0};
		
		initial.iterate();
		sigmas = jessinter.newSigmas(initial, sigmas);
		System.out.println("Initial Design Information");
		initial.print();
		int k = 0;
		while(!jessinter.finished_flag) {
			k++;
			initial = ev.evolve(sigmas, initial);
			sigmas = jessinter.newSigmas(initial, sigmas);
			
			
			if (k > 1000) {
				System.out.println("Solution cannot be found. Please change the user requirements!");
				double[] requirements = initial.getRequirements();
				if (requirements[0] > initial.getR_injection()) {
					System.out.println("Renewable Injection cannot be met.");
				}
				if (requirements[1] < initial.getCO2()) {
					System.out.println("CO2 output cannot be met.");
				}
				if (requirements[2] < initial.getLCOE()) {
					System.out.println("LCOE cannot be met.");
				}
				System.out.println(" ");
				break;
			}
		}
		System.out.println("Final Design Information");
		initial.print();
		
		
		
	}
}
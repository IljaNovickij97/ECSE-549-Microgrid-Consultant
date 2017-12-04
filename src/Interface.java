import java.util.Scanner;
import java.io.*;

import microgrid.*;

/* Class used to interface with the user. Does the following: 
 *  * Prompts for file input
 *  * Presents results
 *  * Provides status of program
 */
public class Interface {
	
	private Scanner scanner = new Scanner(System.in);
	
	public Interface() {
		System.out.println("Welcome to the Microgrid Consultant!");
		
	}
	
	public boolean keep_going() {
		System.out.println("All requirements have been met!");
		return true;
	}
	
	public Individual openFile() {
		Individual individual = new Individual();
		boolean file_opened;
		
		do {
			file_opened = true;
			System.out.println("Please enter the name of the input file:");
			String fileName = (scanner.nextLine());
			try {
				FileReader fileReader = 
		          new FileReader(fileName);

		        BufferedReader bufferedReader = 
		           new BufferedReader(fileReader);
		        
		        // FILE PROCESSING HERE!
		        individual = processFile(bufferedReader);
		        bufferedReader.close();
		        
				
			} catch (FileNotFoundException ex) {
				System.out.println(
		                "Unable to open file '" + 
		                fileName + "'");
				file_opened = false;
			} catch(IOException ex) {
	            System.out.println(
	                    "Error reading file '" 
	                    + fileName + "'");
	            file_opened = false;
			}
		} while (!file_opened);
		System.out.println("File read successfully!");
		return individual;
	} 
	
	private Individual processFile(BufferedReader bufferedReader) throws IOException {
		Individual individual = new Individual();
		
		
		// read past Design info name
		bufferedReader.readLine();
		bufferedReader.readLine();
		individual.setName(bufferedReader.readLine());
		// read past interest rate
		bufferedReader.readLine();
		individual.setInterest_rate(Double.parseDouble(bufferedReader.readLine()));
		// read past requirements
		bufferedReader.readLine();
		bufferedReader.readLine();
		double min_re = Double.parseDouble(bufferedReader.readLine());
		bufferedReader.readLine();
		double max_co2 = Double.parseDouble(bufferedReader.readLine());
		bufferedReader.readLine();
		double max_lcoe = Double.parseDouble(bufferedReader.readLine());
		double[] requirements = {min_re, max_co2, max_lcoe};
		individual.setRequirements(requirements);
		// read past fuel cost
		bufferedReader.readLine();
		individual.setFuel_cost(Double.parseDouble(bufferedReader.readLine()));
		// read past average daily load
		bufferedReader.readLine();
		double[] load_curve = new double[24];
		for (int i=0; i<24;i++) {
			load_curve[i] = Double.parseDouble(bufferedReader.readLine());
		}
		Load load = new Load(load_curve);
		individual.setLoad(load);
		// read past diesel size
		bufferedReader.readLine();
		double rating = Double.parseDouble(bufferedReader.readLine());
		DieselGenerator diesel = new DieselGenerator(rating);
		individual.setDiesel(diesel);
		// read past solar size
		bufferedReader.readLine();
		double size = Double.parseDouble(bufferedReader.readLine());
		SolarModule solar = new SolarModule(size);
		individual.setSolar(solar);
		// read past ESS size, nominal_voltage and dod
		bufferedReader.readLine();
		size = Double.parseDouble(bufferedReader.readLine());
		bufferedReader.readLine();
		double nominal_voltage = Double.parseDouble(bufferedReader.readLine());
		bufferedReader.readLine();
		double dod = Double.parseDouble(bufferedReader.readLine());
		ESS ess = new ESS(size, nominal_voltage, dod);
		individual.setEss(ess);
		
		
		return individual;
	}
	
}

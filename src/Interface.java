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
	
	public Individual readIndividual() {
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
		System.out.println("File opened!");
		
		// FILE PROCESSING HERE!
		
		
		return individual;
	} 
	
	
}

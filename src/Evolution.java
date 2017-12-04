import java.util.Random;
import microgrid.*;

public class Evolution {
	
	private Random rnd = new Random();
	
	public Individual evolve(double[] sigmas, Individual initial) {
		boolean exit_flag = false;
		int k_max = 10;
		int k = 0;
		int n = 3;
		double[] delta = new double[n];
		double kva = initial.getDiesel().getRating();
		double kwp = initial.getSolar().getSize();
		double kwhr = initial.getEss().getSize();
		double nominal_voltage = initial.getEss().getNominal_voltage();
		double dod = initial.getEss().getDod();
		
		DieselGenerator diesel = new DieselGenerator(kva);
		SolarModule solar = new SolarModule(kwp);
		ESS ess = new ESS(kwhr, nominal_voltage, dod);
		
		
		Individual current = new Individual();
		
		while(!exit_flag) {
			current = initial;
			for (int i=0; i<n; i++) {
				delta[i] =  sigmas[i]*((double) java.lang.Math.abs(rnd.nextGaussian()));
			}
			kva += delta[0];
			kwp += delta[1];
			kwhr += delta[2];
			
			if (kva < 0) {
				kva = 0;
			}
			if (kwp < 0) {
				kwp = 0;
			}
			if (kwhr < 0) {
				kwhr = 0;
			}
			
			diesel.setRating(kva);
			solar.setSize(kwp);
			ess.setSize(kwhr);
			current.setDiesel(diesel);
			current.setSolar(solar);
			current.setEss(ess);
			current.iterate();
			
			if(initial.getR_injection() < current.getR_injection() || initial.getCO2() > current.getCO2() || initial.getLCOE() > current.getLCOE()) {
				initial = current;
			}
			
			if (k > k_max) {
				exit_flag = true;
			}
			k++;
			
		}
		return initial;
	}
}

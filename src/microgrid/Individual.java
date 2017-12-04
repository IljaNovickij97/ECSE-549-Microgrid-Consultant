package microgrid;

import java.text.DecimalFormat;

/* This class describes the individual design of a Microgrid in the evolution algorithm
 * 
 */

class Maths {
	public double power (double number, int n) {
		return java.lang.Math.pow(number,n);
	}
	
	public double annuity (double interest, int years) {
		return (interest*power(1+interest,years))/(power(1+interest,years) - 1);
	}
	
}

public class Individual {
	// Fields
	private String name;
	private Maths m = new Maths();
	private Load load;
	private ESS ess;
	private SolarModule solar;
	private DieselGenerator diesel;
	private double interest_rate;
	private double fuel_cost;
	private double r_injection;
	private double co2;
	private double lcoe;
	private double[] requirements = new double[3];
	private double total_generated;
	
	// Constructor
	public Individual() {
		
	}
	
	// Method to be used to calculate everything in the microgrid
	public void iterate() {
		double stored;
		double fedin;
		double litres_used;
		double genset_energy = 0;
		double kw;
		double[] load_curve = load.getLoad_curve();
		double percentage_load;
		
		
		
		// Iterate each module here
		double total_energy = load.getTotal_energy();
		double daytime_load = load.getDaytime_load();
		double average_load = load.getAverage_load();
		ess.iterate();
		double effective_capacity = ess.getEffective_capacity();
		solar.iterate();
		double solar_energy = solar.getSolar_energy();
		
		
		//R_Injection
		if (solar_energy > daytime_load) {
			fedin = daytime_load;
		} else {
			fedin = solar_energy;
		}
		if ((solar_energy-fedin) < effective_capacity) {
			stored = solar_energy-fedin;
			
		} else {
			stored = effective_capacity;
		}
		r_injection = (stored + fedin)/total_energy;
		kw = (0.8)*(diesel.getRating());
		for (int i=0; i < 24; i++) {
			if (kw < load_curve[i]) {
				genset_energy += kw;
				
			} else {
			    genset_energy += load_curve[i];
			}
		}
		
		//CO2
		genset_energy = genset_energy - stored - fedin;
		
		if (genset_energy < 0) {
			genset_energy = 0;
		}
		if (average_load > kw) {
			percentage_load = 1;
		} else {
			percentage_load = average_load/kw;
		}
		litres_used =  (((kw*percentage_load)/(3.9)))*(24.0);

		co2 = litres_used*(2.68);

		//LCOE
		total_generated = genset_energy + stored + fedin;
		double energy_generated = (total_generated)*(365.0);
		double i = interest_rate;
		double solar_annuity = 1000*m.annuity(i, 25)*0.78*solar.getSize();
		double genset_op = fuel_cost*litres_used*365;
		double genset_annuity = m.annuity(i, 10)*500*diesel.getRating();
		double ess_annuity = m.annuity(i, 8)*160*ess.getSize()*ess.getNominal_voltage();

		lcoe = (solar_annuity + genset_op + genset_annuity + ess_annuity)/(energy_generated);
	}
	
	public void print() {
		DecimalFormat f = new DecimalFormat("#0.00");
		
		System.out.println("**PERFORMANCE**");
		System.out.print("Renewable Injection:	");
		System.out.print(f.format(100*getR_injection()));
		System.out.println("%");
		System.out.print("CO2: 			");
		System.out.print(f.format(getCO2()));
		System.out.println(" kg/year");
		System.out.print("LCOE: 			");
		System.out.print(f.format(getLCOE()));
		System.out.println(" $/KWhr");
		System.out.println("**PARAMETERS**");
		System.out.print("Diesel Generator Rating:");
		System.out.print(f.format(getDiesel().getRating()));
		System.out.println(" KVA");
		System.out.print("Solar Module Size:	");
		System.out.print(f.format(getSolar().getSize()));
		System.out.println(" KWp");
		System.out.print("ESS Size:		");
		System.out.print(f.format(getEss().getSize()));
		System.out.println(" KWhr");
		System.out.println(" ");
	}
	
	
		
		

	
	public double getR_injection() {
		return r_injection;
	}
	
	public double getCO2() {
		return co2;
	}
	
	public double getLCOE() {
		return lcoe;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	
	public void setName(String name) {
		this.name = name;
	}

	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}

	public SolarModule getSolar() {
		return solar;
	}

	public void setSolar(SolarModule solar) {
		this.solar = solar;
	}

	public ESS getEss() {
		return ess;
	}

	public void setEss(ESS ess) {
		this.ess = ess;
	}

	public DieselGenerator getDiesel() {
		return diesel;
	}

	public void setDiesel(DieselGenerator diesel) {
		this.diesel = diesel;
	}

	public double getInterest_rate() {
		return interest_rate;
	}

	public void setInterest_rate(double interest_rate) {
		this.interest_rate = interest_rate;
	}

	public double getFuel_cost() {
		return fuel_cost;
	}

	public void setFuel_cost(double fuel_cost) {
		this.fuel_cost = fuel_cost;
	}

	public double[] getRequirements() {
		return requirements;
	}

	public void setRequirements(double[] requirements) {
		this.requirements = requirements;
	}

	public double getTotal_generated() {
		return total_generated;
	}

	public void setTotal_generated(double total_generated) {
		this.total_generated = total_generated;
	}


}


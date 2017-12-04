package microgrid;

public class Load {
	// Fields
	private double[] load_curve = new double[24];
	private double total_energy = 0;
	private double daytime_load = 0;
	private double average_load;
	
	public Load(double[] load_curve) {
		setLoad_curve(load_curve);
		for (int i=0; i < 24; i++) {
			total_energy += load_curve[i];
		}
		average_load = total_energy/24;		
		for (int i=8; i < 13; i++) {
			daytime_load += load_curve[i];
		}
	}
	
	// Setters and Getters
	
	public double[] getLoad_curve() {
		return load_curve;
	}
	public void setLoad_curve(double[] load_curve) {
		this.load_curve = load_curve;
	}

	public double getDaytime_load() {
		return daytime_load;
	}

	public void setDaytime_load(double daytime_load) {
		this.daytime_load = daytime_load;
	}

	public double getTotal_energy() {
		return total_energy;
	}

	public void setTotal_energy(double total_energy) {
		this.total_energy = total_energy;
	}

	public double getAverage_load() {
		return average_load;
	}

	public void setAverage_load(double average_load) {
		this.average_load = average_load;
	}
	
	
}

package microgrid;

public class SolarModule {
	// Fields
	private double size;
	private double solar_energy;
	
	public SolarModule(double size) {
		setSize(size);
	}
	
	// Setters and Getters
	
	public void iterate() {
		this.solar_energy = ((3.8438)*getSize());
	}
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}

	public double getSolar_energy() {
		return solar_energy;
	}

	public void setSolar_energy(double solar_energy) {
		this.solar_energy = solar_energy;
	}
	
}

package microgrid;

public class ESS {
	//Fields
	private double size;
	private double nominal_voltage;
	private double dod;
	private double effective_capacity;
	
	public ESS(double size, double nominal_voltage, double dod) {
		setSize(size);
		setNominal_voltage(nominal_voltage);
		setDod(dod);
	}
	
	public void iterate() {
		setEffective_capacity((getNominal_voltage())*(getSize())*(1-getDod()));
	}
	

	// Getters and setters
	
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}


	public double getNominal_voltage() {
		return nominal_voltage;
	}


	public void setNominal_voltage(double nominal_voltage) {
		this.nominal_voltage = nominal_voltage;
	}

	public double getDod() {
		return dod;
	}

	public void setDod(double dod) {
		this.dod = dod;
	}

	public double getEffective_capacity() {
		return effective_capacity;
	}

	public void setEffective_capacity(double effective_capacity) {
		this.effective_capacity = effective_capacity;
	}

}

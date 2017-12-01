package microgrid;

public class SolarModule {
	// Fields
	private float size;
	private float average_radiation;
	private float capacity_factor;
	private float capital_cost;
	
	public SolarModule(float size, float average_radiation, float capacity_factor) {
		setSize(size);
		setAverage_radiation(average_radiation);
		setCapacity_factor(capacity_factor);
	}
	
	// Setters and Getters
	
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public float getCapacity_factor() {
		return capacity_factor;
	}
	public void setCapacity_factor(float capacity_factor) {
		this.capacity_factor = capacity_factor;
	}
	public float getAverage_radiation() {
		return average_radiation;
	}
	public void setAverage_radiation(float average_radiation) {
		this.average_radiation = average_radiation;
	}
	public float getCapital_cost() {
		return capital_cost;
	}
	public void setCapital_cost(float capital_cost) {
		this.capital_cost = capital_cost;
	}
}

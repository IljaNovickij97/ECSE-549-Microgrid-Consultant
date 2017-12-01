package microgrid;

public class Load {
	// Fields
	private float[] average_load_curve;
	private float yearly_consumption;
	
	public Load(float[] average_load_curve, float yearly_consumption) {
		setAverage_load_curve(average_load_curve);
		setYearly_consumption(yearly_consumption);
	}
	
	// Setters and Getters
	
	public float[] getAverage_load_curve() {
		return average_load_curve;
	}
	public void setAverage_load_curve(float[] average_load_curve) {
		this.average_load_curve = average_load_curve;
	}
	public float getYearly_consumption() {
		return yearly_consumption;
	}
	public void setYearly_consumption(float yearly_consumption) {
		this.yearly_consumption = yearly_consumption;
	}
	
	
}

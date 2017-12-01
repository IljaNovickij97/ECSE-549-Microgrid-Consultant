package microgrid;

public class DieselGenerator {
	//Fields
	
	private float rating;
	private float[] consumption;
	private float co2;
	
	public DieselGenerator(float rating, float[] consumption) {
		setRating(rating);
		setConsumption(consumption);
	}

	// Getters and setters
	
	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public float[] getConsumption() {
		return consumption;
	}

	public void setConsumption(float[] consumption) {
		this.consumption = consumption;
	}

	public float getCo2() {
		return co2;
	}

	public void setCo2(float co2) {
		this.co2 = co2;
	}
	
}

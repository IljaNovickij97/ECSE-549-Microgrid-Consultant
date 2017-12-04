package microgrid;

public class DieselGenerator {
	//Fields
	
	private double rating;
	
	public DieselGenerator(double rating) {
		setRating(rating);
	}

	// Getters and setters
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	
}

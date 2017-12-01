package microgrid;

public class ESS {
	//Fields
	private float size;
	private float cost;
	
	public ESS(float size) {
		setSize(size);
	}
	

	// Getters and setters
	
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}

}

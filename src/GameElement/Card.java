package GameElement;

public class Card {
	
	private String shape;
	private String value;
	private boolean isUpside;
	
	public Card(String shape, String value) {
		this.shape = shape;
		this.value = value;
		
		this.isUpside = true;
	}

	public String getShape() {
		return shape;
	}

	public String getValue() {
		return value;
	}

	public boolean isUpside() {
		return isUpside;
	}
	
	public Card turnCard() {
		this.isUpside = !(this.isUpside);
		return this;
	}
	
	public boolean isHonors() {
		if (this.value == "King" || this.value == "Queen" || this.value == "Jack") {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isAce() {
		if (this.value == "Ace") {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		if (this.isUpside) {
			return "(" + value + " of " + shape + "s)";
		}
		else {
			return "(XXXX - XXXX)";
		}
	}

}

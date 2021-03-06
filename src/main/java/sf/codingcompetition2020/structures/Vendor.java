package sf.codingcompetition2020.structures;

public class Vendor {
	private int vendorId;
	private String area;
	private int vendorRating;
	private boolean inScope;

	public Vendor(int vendorId, String area, int vendorRating, boolean inScope) {
		this.vendorId = vendorId;
		this.area = area;
		this.vendorRating = vendorRating;
		this.inScope = inScope;
	}

	public int getVendorId() {
		return vendorId;
	}
	public String getArea() {
		return area;
	}
	public int getVendorRating() {
		return vendorRating;
	}
	public boolean getInScope() {
		return inScope;
	}
}

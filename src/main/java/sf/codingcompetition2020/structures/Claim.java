package sf.codingcompetition2020.structures;

public class Claim {
	private int claimId;
	private int customerId;
	private boolean closed;
	private int monthsOpen;

	public Claim(int claimId, int customerId, boolean closed, int monthsOpen) {
		this.claimId = claimId;
		this.customerId = customerId;
		this.closed = closed;
		this.monthsOpen = monthsOpen;
	}

	public int getClaimId() {
		return claimId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public boolean getClosed() {
		return closed;
	}
	public int getMonthsOpen() {
		return monthsOpen;
	}
}

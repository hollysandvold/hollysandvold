
public abstract class Reservation {
	private String name;
	
	public Reservation(String client) {
		this.name=client;
	}
	
	public final String reservationName() {
		return this.name;
	}
	
	public abstract int getCost();
		
	public abstract boolean equals(Object o);
}

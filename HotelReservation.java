
public class HotelReservation extends Reservation {
	private Hotel location;
	private String roomType;
	private int numOfNights;
	private int price;
	
	// constructor that initializes the fields and reserves a room of the correct type
	// in the given hotel
	public HotelReservation (String name, Hotel hotel, String roomType, int numOfNights) {
		super(name);
		this.location=hotel;
		this.roomType=roomType;
		this.numOfNights=numOfNights;

		try {
			Room temp= new Room(this.roomType);
			this.price = temp.getPrice();
			this.location.reserveRoom(roomType);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("This reservation is not possible");
		}
	}

	//a method to retrieve the number of the night on the reservation
	public int getNumOfNights() {
		return numOfNights;
	}
	
	//a method to retrieve the cost of the reservation
	public int getCost() {
		int cost = this.price*this.numOfNights;
		return cost;
	}
	
	//method to compare hotel reservations
	public boolean equals(Object o) {
		if(o instanceof HotelReservation) {
			// to check the rest, cast o into a hotel reservation and store it in p
			HotelReservation p = (HotelReservation) o;
			// check the remaining requirements
			if(this.reservationName().equals(p.reservationName()) &&
				this.roomType==p.roomType && 
				this.numOfNights==p.numOfNights &&
				this.location==p.location &&
				this.getCost()==p.getCost()) {		
				return true;
			}
		}
		return false;
	}
}

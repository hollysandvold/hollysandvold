public class BnBReservation extends HotelReservation{
	
	//constructor that calls the Hotel Reservation constructor 
	public BnBReservation (String name, Hotel hotel, String roomType, int numOfNights) {
		super(name, hotel, roomType, numOfNights);
	}
	
	//method to get the cost
	public int getCost(){
		int cost = super.getCost();
		int nights = super.getNumOfNights();
		cost = cost + 1000*nights;
		return cost;
	}
}

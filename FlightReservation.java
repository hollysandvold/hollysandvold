
public class FlightReservation extends Reservation{
	private Airport departure;
	private Airport arrival;
	
	public FlightReservation(String name, Airport departure, Airport arrival) {
		super(name);
		
		if(departure.equals(arrival)) {
			throw new IllegalArgumentException(
					"Airport of departure and arrival cannot be the same");
		} else {
			this.departure=departure;
			this.arrival=arrival;
		}
	}
	
	public int getCost() {
		int cost=0;
		int distance = Airport.getDistance(departure, arrival);
		int feesOne = departure.getFees();
		int feesTwo = arrival.getFees();
		
		//find cost of fuel
		double tempFuelCost = (distance/167.52)*124;
		int fuel = (int) (Math.ceil(tempFuelCost));
		
		//find total cost
		cost = fuel + feesOne + feesTwo + 5375;
		
		return cost;
	}
	
	// method to check if the flight reservations are the same
	public boolean equals(Object o) {
		if(o instanceof FlightReservation) {
			FlightReservation p = (FlightReservation) o;
			if(this.reservationName() == p.reservationName() &&
					this.departure==p.departure 
					&& this.arrival==p.arrival) {		
			return true;
			}
		}
		return false;
	}
}

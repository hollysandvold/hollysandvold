public class Customer {
	private String name;
	private int balance;
	private Basket basket;
	
	//constructor
	public Customer (String name, int balance) {
		this.name = name;
		this.balance = balance;
		this.basket = new Basket();
	}
	
	//method to get the name of the customer
	public String getName() {
		return this.name;
	}
	
	//method to get the balance of the customer
	public int getBalance() {
		return this.balance;
	}
	
	// method that returns a reference to the basket
	public Basket getBasket() {
		return basket;
	}
	
	//method to add funds to the balance
	public int addFunds(int newFunds) {
		if (newFunds<0) {
			throw new IllegalArgumentException("Please add funds of a positive amount");
		} else {
			this.balance = this.balance + newFunds;
			return this.balance;
		}
	}
	
	//method to add items to the basket
	public int addToBasket(Reservation newReservation) {
		// check if the name of the customer is the same
		if(newReservation.reservationName()==this.name) {
			// add reservation to basket
			return this.basket.add(newReservation);

		} else {
			throw new IllegalArgumentException("Your reservation could not be added to your basket");
		}
	}
	
	// method to add a hotel reservation to the basket 
	public int addToBasket(Hotel hotel, String roomType, int numOfNights, boolean breakfast) {
		if (breakfast == false) {
			HotelReservation newHotelRes = new HotelReservation(this.name, hotel, roomType, numOfNights);
			this.basket.add(newHotelRes);
			return this.basket.getNumOfReservations();
		} else {
			BnBReservation newBnbRes = new BnBReservation(this.name, hotel, roomType, numOfNights);
			return this.basket.add(newBnbRes);
		}
	}
	
	//method to add an flight reservation to the basket
	public int addToBasket(Airport departure, Airport arrival) {
		FlightReservation newFlightRes = new FlightReservation(this.name, departure, arrival);
		return this.basket.add(newFlightRes);
	}
	
	//method to remove a reservation from basket and returns true if it is successful
	public boolean removeFromBasket(Reservation remove) {
		return this.basket.remove(remove);
	}
	
	// method to check out and return balance
	public int checkOut() {
		if(balance < this.basket.getTotalCost()) {
			throw new IllegalStateException(
					"You do not have enough money in your account to check out");
		} else {
			this.balance = this.balance - this.basket.getTotalCost();
			this.basket.clear();
			return this.balance;
		}
	}
}

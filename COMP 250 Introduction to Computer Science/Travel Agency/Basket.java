
public class Basket {
	private Reservation[] reservations;
 
	 //constructor that takes no inputs and initalizes the reservations array with nothing in it
	 public Basket() {
		 this.reservations = new Reservation[0];
	 }
	 
	 // method to create a shallow copy of the reservations in a basket
	 public Reservation[] getProducts() {
		 Reservation[] copy = new Reservation[this.reservations.length];
		 
		 for(int i = 0; i<this.reservations.length; i++) {
			 copy[i]=this.reservations[i];
		 }
		 
		 return copy;
	 }
	 
	 // method to add another reservation that returns the number of reservations 
	 public int add(Reservation res) {
		 // create a place holder to copy the current reservations into 
		 Reservation[] copy = new Reservation[this.reservations.length + 1];
		 
		 // copy the existing reservations
		 for (int i = 0; i<this.reservations.length; i++) {
			 copy[i] = this.reservations[i];
		 }
		 
		 // add the final new reservation
		 copy[this.reservations.length]=res;
		 
		 // make the field point to the copy
		 this.reservations = copy;
		 
		 // return the number of reservations
		 int numOfReservations = this.reservations.length;
		 return numOfReservations;
		 
	 }

	// method to remove a reservation and returns true if successful
	 public boolean remove (Reservation res) {
		//create place holder
		 Reservation[] temp = new Reservation[(this.reservations.length)-1];
		 
		 // begin iterating 
		 for (int i = 0; i<temp.length; i++) {
			 // loop if reservation is found
			 if(this.reservations[i].equals(res)) {
				 for(int j = i; j<temp.length; j++) {
					 temp[j]=this.reservations[j+1];
				 }
				 this.reservations=temp;
				 return true;
			 } 
			 // if reservation is not found, continue as normal
			 else {
				 temp[i]=this.reservations[i];
			 }
		 }
		 
		 return false;
	 }
	 
	 // method to clear an array
	 public void clear() {
		Reservation[] clear = new Reservation[0];
		this.reservations = clear;
	 }
	 
	 // method to return the number of reservations in the basket
	 public int getNumOfReservations() {
		 return this.reservations.length;
	 }
	 
	 // method to return the cost of the all the reservations in the basket
	 public int getTotalCost() {
		 int totalCost = 0;
		 for(int i = 0; i<this.reservations.length; i++) {
			 totalCost = totalCost + reservations[i].getCost();
		 }
		 return totalCost;
	 }
	 
}

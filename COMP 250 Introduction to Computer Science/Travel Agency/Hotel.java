
public class Hotel {
	private String name;
	private Room[] rooms;
	
	// constructor that makes a deep copy of the given Room[]
	public Hotel(String name, Room[] rooms) {
		this.name=name;
		Room[] deepCopy = new Room[rooms.length];
		for(int i=0; i<deepCopy.length; i++) {
			Room temp = new Room(rooms[i]);
			deepCopy[i]=temp;
		}
		this.rooms = deepCopy;
	}
	
	// method to make a reservation of a given type and return the price of the reserved room
	public int reserveRoom(String type) {
		Room available = Room.findAvailableRoom(this.rooms, type);
		
		if(available==null) {
			throw new IllegalArgumentException(
					"There are no available rooms of this type");
		} 
		
		available.changeAvailability();
		System.out.println(available.getPrice());
		System.out.println(available);
		return available.getPrice();
	}
	
	// method to cancel a reservation of the given room tyoe
	public boolean cancelRoom(String type) {
		return Room.makeRoomAvailable(this.rooms, type);
	}
}


public class Room {
	private String type;
	private int price;
	private boolean availability=false;
	
	// constructor that builds a room given a type
	public Room (String type) {
		if(type.equalsIgnoreCase("double")){
			this.type="double";
			this.price=9000;
			this.availability=true;
		} else if (type.equalsIgnoreCase("queen")) {
			this.type="queen";
			this.price=11000;
			this.availability=true;
		} else if (type.equalsIgnoreCase("king")) {	
			this.type=("king");
			this.price=15000;	
			this.availability=true;
		} else {
			throw new IllegalArgumentException("No room of such type can be created.");
		}
	}
	
	//constructor that creates a copy of a preexisting room
	public Room (Room inputRoom) {
		this.type=inputRoom.type;
		this.price=inputRoom.price;
		this.availability=inputRoom.availability;
	}
	
	// get type method
	public String getType() {
		return this.type;
	}
	
	// get price method
	public int getPrice() {
		return this.price;
	}
	
	//method to flip the availability of a room
	public void changeAvailability() {
		if(this.availability==true) {
			this.availability=false;
		} else if (this.availability!=true) {
			this.availability=true;
		}
	}
	
	// find first available room of the given type
	public static Room findAvailableRoom(Room[] rooms, String roomType) {
		Room temp=null;

		for(int i =0; i<rooms.length; i++) {
			if(rooms[i].availability==true && rooms[i].type==roomType) {
				temp=rooms[i];
				break;
			}
		}
		
		return temp;
	}
	
	// make first unavailable room of given type available
	public static boolean makeRoomAvailable(Room[] rooms, String roomType) {
		boolean success = false;
		for(int i=0; i<rooms.length; i++) {
			if(rooms[i].availability==false && rooms[i].type==roomType) {
				System.out.println(rooms[i]);
				rooms[i].changeAvailability();
				success = true;
				return success;
			}
		}
		return success;
	}
}

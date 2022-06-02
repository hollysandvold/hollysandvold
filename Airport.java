public class Airport{
	private int xCoord;
	private int yCoord;
	private int fees;
	
	//constructor to initialize fields
	public Airport(int x, int y, int fees) {
		this.xCoord =x;
		this.yCoord=y;
		this.fees=fees;
	}
	
	//retrieve airport fees
	public int getFees() {
		return this.fees;
	}
	
	//gives distance between 2 airports
	public static int getDistance(Airport one, Airport two) {
		//set x and y coordinates
		int oneX=one.xCoord;
		int twoX=two.xCoord;
		int oneY=one.yCoord;
		int twoY=two.yCoord;
		
		// find distance
		double distance=(Math.sqrt(Math.pow((oneX-twoX), 2.0)+
				Math.pow((oneY-twoY),2.0)));
		
		// convert to int and round up
		int roundUp= (int)distance;
		if(distance%1!=0) {
			roundUp++;
		}
		
		// return rounded number
		return roundUp;
	}
}

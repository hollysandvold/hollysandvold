/*
 * Holly Sandvold 260788799
 */

import java.util.ArrayList; 
import java.util.Random; 
 
public class Toy { 
    // static attributes 
    private static String[] names = {"Bob", "Penny", "Fisher", "Snoopy", "Garfield", "Mary", "Chuchu", "Trooper", "Lovebug", "Bella"}; 
    private static String[] colors = {"red", "blue", "green", "yellow", "orange", "purple"}; 
    private static String[] types = {"car", "doll", "stuffed cat", "train", "ball", "kite", "teddy bear", "trike"}; 
    private static Random r = new Random(); 
   
    // additional private attributes
    private String name;
    private String color;
    private String type;
    private int experience;
    
    public static void main (String[] args){

    }
    
    // private static methods provided 
    private static String getRandomName() { 
        int i = r.nextInt(names.length); 
        return names[i]; 
    } 
     
    private static String getRandomColor() { 
        int i = r.nextInt(colors.length); 
        return colors[i]; 
    } 
     
    private static String getRandomType() { 
        int i = r.nextInt(types.length); 
        return types[i]; 
    } 
     
    // first constructor: initiates a toy with given values
    public Toy (String name, String color, String type, int experience){
      this.name = name;
      this.color = color;
      this.type = type;
      this.experience = experience;
    }
    
    // second constructor: creates a random toy
    public Toy(){
      this.name = getRandomName();
      this.color = getRandomColor();
      this.type = getRandomType();
      int randomExperience = r.nextInt(15)+10;
      this.experience = randomExperience;
    }
     
    // get methods
    public String getName(){
      return this.name;
    }
    
    public String getColor() {
      return this.color;
    } 
     
    public String getType(){
      return this.type;
    }
     
    public int getXp() {
      return this.experience;
    }
    
    // toString method 
    public String toString(){
      return this.name + " the " + this.color + " " + this.type;
    }
    
    // create a new toy
    public static Toy createToy(String info){
      String[] split = info.split("\t");
      // if there aren't 4 pieces of info given, throuw IllegalArgumentException
      if(split.length != 4){
        throw new IllegalArgumentException("Please use 4 pieces of information");
      }
      // save all the pieces of info
      String name = split[0].trim();
      String color = split[1].trim();
      String type = split[2].trim();
      int experience = Integer.parseInt(split[3].trim());
      // create new toy with given info
      Toy newToy = new Toy(name, color, type, experience);
      return newToy;
    }
    
    // a method to get the toy with the greatest XP
    public static Toy findBestToy(ArrayList<Toy> toys){
      // if there are no toys, catch the NullPointerException
      if (toys.size()==0){
        return null;
      }
      // initalize temp best toy
      Toy bestToy = new Toy(null, null, null, 0);
      // loops through all toys to find the one with the highest Xp
      for(int i = toys.size()-1; i>=0; i--){
        Toy temp = toys.get(i);
        if(temp.getXp() >= bestToy.getXp()){
          bestToy=toys.get(i);
        }
      }
      
      return bestToy;
    }
      
}